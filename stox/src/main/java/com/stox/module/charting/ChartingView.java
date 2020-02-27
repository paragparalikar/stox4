package com.stox.module.charting;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import com.stox.fx.fluent.beans.binding.FluentStringBinding;
import com.stox.fx.fluent.scene.control.FluentSplitPane;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentStackPane;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.axis.horizontal.MutableXAxis;
import com.stox.module.charting.axis.vertical.TransformerYAxis;
import com.stox.module.charting.chart.Chart;
import com.stox.module.charting.chart.PrimaryChart;
import com.stox.module.charting.chart.SecondaryChart;
import com.stox.module.charting.drawing.DrawingRepository;
import com.stox.module.charting.event.ConfigChangedEvent;
import com.stox.module.charting.event.DataChangedEvent;
import com.stox.module.charting.event.PanRequestEvent;
import com.stox.module.charting.event.PlotRemovedEvent;
import com.stox.module.charting.event.UnderlayChangedEvent;
import com.stox.module.charting.event.ZoomRequestEvent;
import com.stox.module.charting.grid.VerticalGrid;
import com.stox.module.charting.plot.DerivativePlot;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.plot.VolumePlot;
import com.stox.module.charting.tools.ChartingToolBar;
import com.stox.module.charting.unit.PriceUnitType;
import com.stox.module.charting.widget.BarInfoPanel;
import com.stox.module.charting.widget.Crosshair;
import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.BarRepository;
import com.stox.workbench.link.Link;
import com.stox.workbench.link.Link.State;
import com.stox.workbench.module.ModuleView;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import lombok.Getter;
import lombok.NonNull;

public class ChartingView extends ModuleView<ChartingViewState> {

	private long to;
	private BarSpan barSpan = BarSpan.D;
	private ModeMouseHandler mouseModeHandler;

	private final PrimaryChart primaryChart;
	private final FxMessageSource messageSource;
	private final MutableXAxis xAxis = new MutableXAxis();
	private final VerticalGrid verticalGrid = new VerticalGrid();
	private final BarInfoPanel barInfoPanel = new BarInfoPanel();
	private final Configuration configuration = new Configuration();
	private final TransformerYAxis volumeYAxis = new TransformerYAxis();
	private final Set<SecondaryChart> secondaryCharts = new HashSet<>();
	private final Consumer<State> stateConsumer = this::linkStateChanged;
	private final ChartingContextMenu contextMenu = new ChartingContextMenu();
	private final VolumePlot volumePlot = new VolumePlot(configuration, volumeYAxis);
	@Getter
	private final ChartingTitleBar titleBar = new ChartingTitleBar(this::linkStateChanged);
	private final FluentSplitPane splitPane = new FluentSplitPane().orientation(Orientation.VERTICAL).classes("charting-split-pane");;
	private final Crosshair crosshair = new Crosshair(splitPane);
	private final PanAndZoomMouseHandler panAndZoomMouseHandler = new PanAndZoomMouseHandler(splitPane);
	private final FluentStackPane root = new FluentStackPane(verticalGrid, splitPane, crosshair.getNode()).classes("charting-root");

	public ChartingView(@NonNull final ExecutorService executorService, @NonNull final FxMessageSource messageSrouce, @NonNull final BarRepository barRepository,
			@NonNull final DrawingRepository drawingRepository) {
		this.messageSource = messageSrouce;
		title(titleBar).content(root);
		primaryChart = new PrimaryChart(configuration, xAxis, volumeYAxis, verticalGrid, barInfoPanel, executorService, barRepository, drawingRepository);
		splitPane.getItems().add(primaryChart.container());
		primaryChart.add(volumePlot);
		tool(new ChartingToolBar(this, messageSource));
		bind();
	}

	@Override
	public ModuleView<ChartingViewState> start(ChartingViewState state, Bounds bounds) {
		linkChanged(null, titleBar.getLinkButton().getLink());
		mouseModeHandler(panAndZoomMouseHandler);
		return super.start(state, bounds);
	}

	@Override
	protected ModuleView<ChartingViewState> defaultBounds(@NonNull final FluentBorderPane container, @NonNull final Bounds bounds) {
		container.layoutX(bounds.getWidth() / 5).width(4 * bounds.getWidth() / 5).height(bounds.getHeight());
		return this;
	}

	private void bind() {
		splitPane
				.addFilter(MouseEvent.MOUSE_MOVED, event -> showIndexInfo(event.getX()))
				.addFilter(MouseEvent.MOUSE_DRAGGED, event -> showIndexInfo(event.getX()))
				.addHandler(MouseEvent.MOUSE_PRESSED, this::showContextMenu)
				.addHandler(UnderlayChangedEvent.TYPE, event -> add(remove(event.plot())))
				.addHandler(ConfigChangedEvent.TYPE, event -> configChanged(event.plot()))
				.addHandler(ZoomRequestEvent.TYPE, event -> zoom(event.x(), event.percentage()))
				.addHandler(PanRequestEvent.TYPE, event -> pan(event.deltaX()))
				.addHandler(PlotRemovedEvent.TYPE, event -> removeEmptyCharts())
				.addHandler(DataChangedEvent.TYPE, event -> barDataChanged(event.bars()));
	}

	public void barDataChanged(final List<Bar> bars) {
		final Scrip scrip = primaryChart.scrip();
		xAxis.setBars(bars);
		primaryChart.load(scrip, bars);
		secondaryCharts.forEach(chart -> chart.load(scrip, bars));
		redraw();
	}

	private void redraw() {
		updateValueBounds();
		Ui.fx(() -> layoutChartChildren());
	}

	private void updateValueBounds() {
		primaryChart.updateValueBounds();
		secondaryCharts.forEach(Chart::updateValueBounds);
	}

	private void layoutChartChildren() {
		primaryChart.layoutChartChildren();
		secondaryCharts.forEach(Chart::layoutChartChildren);
	}

	public void clearDrawings() {
		primaryChart.clearDrawings();
		secondaryCharts.forEach(Chart::clearDrawings);
	}

	private void relayoutCharts() {
		final int size = splitPane.getItems().size();
		for (int index = 0; index < size - 1; index++) {
			splitPane.setDividerPosition(index, 1 - ((size - index - 1) * 0.2));
		}
	}

	private DerivativePlot<?> remove(final DerivativePlot<?> plot) {
		primaryChart.remove(plot);
		secondaryCharts.forEach(chart -> chart.remove(plot));
		removeEmptyCharts();
		return plot;
	}

	private void removeEmptyCharts() {
		final Iterator<SecondaryChart> secondaryChartIterator = secondaryCharts.iterator();
		while (secondaryChartIterator.hasNext()) {
			final SecondaryChart secondaryChart = secondaryChartIterator.next();
			if (secondaryChart.isEmpty()) {
				secondaryChartIterator.remove();
				splitPane.getItems().remove(secondaryChart.container());
				relayoutCharts();
			}
		}
	}

	public DerivativePlot<?> add(final DerivativePlot<?> plot) {
		if (Underlay.PRICE.equals(plot.underlay())) {
			add(primaryChart, plot);
		} else if (Underlay.VOLUME.equals(plot.underlay())) {
			if (primaryChart.contains(volumePlot)) {
				add(primaryChart, plot);
			} else {
				secondaryCharts.stream().filter(chart -> chart.contains(volumePlot)).findFirst().ifPresent(chart -> add(chart, plot));
			}
		} else {
			final SecondaryChart secondaryChart = new SecondaryChart(configuration, xAxis, volumeYAxis);
			secondaryCharts.add(secondaryChart);
			splitPane.getItems().add(secondaryChart.container());
			add(secondaryChart, plot);
			relayoutCharts();
		}
		return plot;
	}

	private void add(Chart chart, DerivativePlot<?> plot) {
		chart.add(plot);
		plot.load(primaryChart.bars());
		chart.updateValueBounds();
		chart.layoutChartChildren();
	}

	public void load(final DerivativePlot<?> plot) {
		plot.load(primaryChart.bars());
	}

	private void linkChanged(final Link old, final Link link) {
		Optional.ofNullable(old).ifPresent(o -> old.remove(stateConsumer));
		Optional.ofNullable(link).ifPresent(l -> {
			link.add(stateConsumer);
			stateConsumer.accept(link.getState());
		});
	}

	private void linkStateChanged(final State state) {
		Optional.ofNullable(state).ifPresent(s -> {
			unload();
			reset();
			this.to = state.to();
			primaryChart.scrip(state.scrip());
			barSpan = Optional.ofNullable(state.barSpan()).orElse(barSpan);
			updateTitleText();
			primaryChart.load(to, this.barSpan, xAxis);
		});
	}

	public ChartingView barSpan(final BarSpan barSpan) {
		if (null != barSpan && !barSpan.equals(this.barSpan)) {
			unload();
			reset();
			this.barSpan = barSpan;
			updateTitleText();
			primaryChart.load(to, barSpan, xAxis);
		}
		return this;
	}

	public void unload() {
		final Scrip scrip = primaryChart.scrip();
		primaryChart.unload(scrip);
		secondaryCharts.forEach(chart -> chart.unload(scrip));
	}

	public void reset() {
		primaryChart.reset();
		secondaryCharts.forEach(Chart::reset);
		xAxis.setPivotX(primaryChart.content().getWidth() - xAxis.getUnitWidth() * configuration.rightGapBarCountProperty().get());
	}

	private void updateTitleText() {
		final Scrip scrip = primaryChart.scrip();
		final ObservableValue<String> barSpanValue = messageSource.get(barSpan.getName());
		getTitleBar().title(new FluentStringBinding(() -> {
			return scrip.getName() + " - " + barSpanValue.getValue();
		}, barSpanValue));
	}

	public ChartingView mouseModeHandler(final ModeMouseHandler mouseModeHandler) {
		Optional.ofNullable(this.mouseModeHandler).ifPresent(ModeMouseHandler::detach);
		this.mouseModeHandler = Optional.ofNullable(mouseModeHandler).orElse(panAndZoomMouseHandler);
		this.mouseModeHandler.attach();
		return this;
	}

	private void showIndexInfo(final double x) {
		final int index = xAxis.getIndex(x);
		primaryChart.showIndexInfo(index);
		secondaryCharts.forEach(chart -> chart.showIndexInfo(index));
	}

	private void showContextMenu(final MouseEvent event) {
		if (MouseButton.SECONDARY.equals(event.getButton()) && !event.isConsumed()) {
			final Window window = getNode().getScene().getWindow();
			contextMenu.show(window, event.getScreenX(), event.getScreenY());
		}
	}

	private void configChanged(final DerivativePlot<?> plot) {
		plot.load(primaryChart.bars());
		plot.updateValueBounds(xAxis.getClippedStartIndex(), xAxis.getClippedEndIndex());
		Optional.ofNullable(chart(plot)).ifPresent(Chart::layoutChartChildren);
	}

	public Chart chart(final double screenX, final double screenY) {
		final Point2D point = new Point2D(screenX, screenY);
		return primaryChart.container().contains(primaryChart.container().screenToLocal(point)) ? primaryChart
				: secondaryCharts.stream().filter(chart -> chart.container().contains(chart.container().screenToLocal(point))).findFirst().orElse(null);
	}

	private Chart chart(DerivativePlot<?> plot) {
		if (primaryChart.contains(plot)) {
			return primaryChart;
		}
		return secondaryCharts.stream().filter(chart -> chart.contains(plot)).findFirst().orElse(null);
	}

	public Date date() {
		final double x = contextMenu.getAnchorX();
		final Point2D point = primaryChart.container().screenToLocal(x, 0);
		final int index = xAxis.getIndex(point.getX());
		final List<Bar> bars = primaryChart.bars();
		if (index >= 0 && index < bars.size()) {
			final Bar bar = bars.get(index);
			if (null != bar) {
				return new Date(bar.getDate());
			}
		}
		return null;
	}

	private void zoom(final double x, final int percentage) {
		xAxis.zoom(0 == x ? xAxis.getUnitWidth() : x, percentage);
		redraw();
		primaryChart.load(to, barSpan, xAxis);
	}

	private void pan(final double deltaX) {
		xAxis.pan(deltaX);
		redraw();
		primaryChart.load(to, barSpan, xAxis);
	}

	public PriceUnitType unitType() {
		return primaryChart.unitType();
	}

	public ChartingView unitType(final PriceUnitType unitType) {
		primaryChart.unitType(unitType);
		return this;
	}

	@Override
	public ChartingViewState stop(Bounds bounds) {
		unload();
		return super.stop(new ChartingViewState(), bounds);
	}

}
