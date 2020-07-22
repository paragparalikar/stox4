package com.stox.module.charting;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

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
import com.stox.module.charting.drawing.DrawingStateRepository;
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
import com.stox.module.core.model.intf.CoreConstant;
import com.stox.module.core.model.intf.HasBarSpan;
import com.stox.module.core.model.intf.HasDate;
import com.stox.module.core.model.intf.HasScrip;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.workbench.link.LinkState;
import com.stox.workbench.module.ModuleTitleBar;
import com.stox.workbench.module.ModuleView;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class ChartingView extends ModuleView<ChartingViewState> implements HasScrip, HasBarSpan, HasDate{

	private long to;
	@Getter
	private BarSpan barSpan = BarSpan.D;
	private ModeMouseHandler mouseModeHandler;
	
	@Getter
	private final ChartingTitleBar titleBar;
	
	@Getter
	private final Configuration configuration = new Configuration();
	
	@Getter
	private final ChartingContextMenu contextMenu = new ChartingContextMenu(this);

	private final PrimaryChart primaryChart;
	private final FxMessageSource messageSource;
	private final ScripRepository scripRepository;
	private final MutableXAxis xAxis = new MutableXAxis();
	private final VerticalGrid verticalGrid = new VerticalGrid();
	private final BarInfoPanel barInfoPanel = new BarInfoPanel();
	private final TransformerYAxis volumeYAxis = new TransformerYAxis();
	private final Set<SecondaryChart> secondaryCharts = new HashSet<>();
	private final Consumer<LinkState> linkStateConsumer = this::linkStateChanged;
	private final VolumePlot volumePlot = new VolumePlot(configuration, volumeYAxis);
	private final FluentSplitPane splitPane = new FluentSplitPane().orientation(Orientation.VERTICAL).classes("charting-split-pane");;
	private final Crosshair crosshair = new Crosshair(splitPane);
	private final PanAndZoomMouseHandler panAndZoomMouseHandler = new PanAndZoomMouseHandler(splitPane);
	private final FluentStackPane root = new FluentStackPane(verticalGrid, splitPane, crosshair.getNode()).classes("charting-root");

	public ChartingView(
			@NonNull final ExecutorService executorService, 
			@NonNull final FxMessageSource messageSrouce, 
			@NonNull final BarRepository barRepository,
			@NonNull final ScripRepository scripRepository, 
			@NonNull final DrawingStateRepository drawingStateRepository) {
		this.messageSource = messageSrouce;
		this.scripRepository = scripRepository;
		title(titleBar = new ChartingTitleBar(messageSource, this::linkStateChanged)).content(root);
		primaryChart = new PrimaryChart(configuration, xAxis, volumeYAxis, verticalGrid, barInfoPanel, executorService, barRepository, drawingStateRepository);
		splitPane.getItems().add(primaryChart.container());
		primaryChart.add(volumePlot);
		tool(new ChartingToolBar(this, messageSource));
		titleBar.getLinkButton().add(linkStateConsumer);
		bind();
	}

	@Override
	public ModuleView<ChartingViewState> start(final ChartingViewState state, final Bounds bounds) {
		super.start(state, bounds);
		mouseModeHandler(panAndZoomMouseHandler);
		Platform.runLater(() -> {
			linkStateConsumer.accept(titleBar.getLinkButton().getLink().getState());
			Optional.ofNullable(state).ifPresent(this::state);
		});
		return this;
	}

	private void state(final ChartingViewState state) {
		/*
		 * this.to = state.to(); this.barSpan =
		 * Optional.ofNullable(state.barSpan()).orElse(BarSpan.D);
		 * xAxis.state(state.mutableXAxisState());
		 * primaryChart.state(state.primaryChartState());
		 * Optional.ofNullable(state.secondaryChartStates()).ifPresent(states -> {
		 * states.forEach(secondaryChartState -> { final SecondaryChart secondaryChart =
		 * new SecondaryChart(configuration, xAxis, volumeYAxis);
		 * secondaryCharts.add(secondaryChart);
		 * splitPane.getItems().add(secondaryChart.container());
		 * secondaryChart.state(secondaryChartState);
		 * secondaryChartState.derivativePlotStates().forEach(plotState -> {
		 * Optional.ofNullable(plotState.plot(configuration)).ifPresent(plot -> {
		 * add(secondaryChart, (DerivativePlot<?, ?>) plot); }); }); });
		 * relayoutCharts(); });
		 */
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

	public void removeDrawings() {
		primaryChart.clearDrawings();
		primaryChart.removeDrawings();
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

	public Chart add(final DerivativePlot<?> plot) {
		if (Underlay.PRICE.equals(plot.underlay())) {
			return add(primaryChart, plot);
		} else if (Underlay.VOLUME.equals(plot.underlay())) {
			if (primaryChart.contains(volumePlot)) {
				return add(primaryChart, plot);
			} else {
				return secondaryCharts.stream()
						.filter(chart -> chart.contains(volumePlot))
						.findFirst()
						.filter(Objects::nonNull)
						.map(chart -> add(chart, plot))
						.orElse(null);
			}
		} else {
			final SecondaryChart secondaryChart = new SecondaryChart(configuration, xAxis, volumeYAxis);
			secondaryCharts.add(secondaryChart);
			splitPane.getItems().add(secondaryChart.container());
			add(secondaryChart, plot);
			relayoutCharts();
			return secondaryChart;
		}
	}

	private Chart add(Chart chart, DerivativePlot<?> plot) {
		chart.add(plot);
		plot.load(primaryChart.bars());
		chart.updateValueBounds();
		chart.layoutChartChildren();
		return primaryChart;
	}
	
	private void load() {
		updateTitleText();
		primaryChart.load(to, barSpan, xAxis);
	}

	public void load(final DerivativePlot<?> plot) {
		plot.load(primaryChart.bars());
	}

	private void linkStateChanged(final LinkState linkState) {
		Optional.ofNullable(linkState).ifPresent(s -> {
			unload();
			reset();
			this.to = linkState.getLong(CoreConstant.KEY_TO, 0l);
			primaryChart.scrip(scripRepository.find(linkState.get(CoreConstant.KEY_ISIN)));
			barSpan = Optional.ofNullable(BarSpan.byShortName(linkState.get(CoreConstant.KEY_BARSPAN))).orElse(barSpan);
			load();
		});
	}

	public ChartingView barSpan(final BarSpan barSpan) {
		if (null != barSpan && !barSpan.equals(this.barSpan)) {
			unload();
			reset();
			this.barSpan = barSpan;
			load();
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
		titleBar().title(barSpan, primaryChart.scrip());
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
				return new Date(bar.date());
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
		final ChartingViewState chartingViewState = new ChartingViewState()
		/*
		 * .to(to) .barSpan(barSpan) .mutableXAxisState(xAxis.state())
		 * .primaryChartState(primaryChart.state())
		 * .secondaryChartStates(secondaryCharts.stream().map(Chart::state)
		 * .collect(Collectors.toSet()))
		 */;
		unload();
		return super.stop(chartingViewState, bounds);
	}

	@Override
	public Scrip scrip() {
		return primaryChart.scrip();
	}

	@Override
	public ModuleTitleBar getTitleBar() {
		return titleBar();
	}

}
