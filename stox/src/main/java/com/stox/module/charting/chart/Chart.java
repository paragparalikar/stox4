package com.stox.module.charting.chart;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.widget.NoLayoutPane;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.DelegatingYAxis;
import com.stox.module.charting.axis.vertical.MutableYAxis;
import com.stox.module.charting.axis.vertical.ValueAxis;
import com.stox.module.charting.drawing.Drawing;
import com.stox.module.charting.drawing.event.DrawingRemoveRequestEvent;
import com.stox.module.charting.event.PlotRemovedEvent;
import com.stox.module.charting.event.UpdatableRequestEvent;
import com.stox.module.charting.plot.DerivativePlot;
import com.stox.module.charting.plot.Plot;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.plot.info.PlotInfoPane;
import com.stox.module.core.model.Bar;
import com.stox.module.core.model.Scrip;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public abstract class Chart<S extends ChartState> {
	private static final Color[] COLORS = { Color.BLUE, Color.BLUEVIOLET, Color.BROWN, Color.CRIMSON, Color.RED, Color.AQUA, Color.DARKGREEN };
	
	private ValueAxis valueAxis;
	
	private final XAxis xAxis;
	private final Configuration configuration;
	private final DelegatingYAxis volumeYAxis;
	
	private final VBox plotInfoContainer = new VBox();
	private final MutableYAxis yAxis = new MutableYAxis();
	@Getter(AccessLevel.PACKAGE)
	private final Set<Drawing<?>> drawings = new HashSet<>();
	private final Set<DerivativePlot<?,?>> plots = new HashSet<>();
	private final Pane content = new NoLayoutPane().classes("content");
	private final FluentBorderPane container = new FluentBorderPane(content).classes("chart").child(plotInfoContainer);

	public abstract S state();
	
	public Chart<S> state(S state){
		Optional.ofNullable(state).ifPresent(value -> yAxis.state(state.mutableYAxisState()));
		return this;
	}
	
	public S fill(final S state) {
		state.mutableYAxisState(yAxis.state()).derivativePlotStates(plots.stream().map(Plot::state).collect(Collectors.toSet()));
		return state;
	}
	
	protected Chart<S> bind() {
		content.heightProperty().addListener((o, old, value) -> {
			yAxis.setHeight(value.doubleValue());
			updateValueBounds();
			layoutChartChildren();
		});
		container.addEventHandler(UpdatableRequestEvent.TYPE, event -> event.updatable().update(xAxis, yAxis));
		container.addEventHandler(DrawingRemoveRequestEvent.TYPE, event -> remove(event.drawing()));
		return this;
	}
	
	public Chart<S> valueAxis(@NonNull ValueAxis valueAxis) {
		container.right(this.valueAxis = valueAxis);
		return this;
	}
	
	public Chart<S> load(final Scrip scrip, final List<Bar> bars) {
		plots.forEach(plot -> plot.load(bars));
		return this;
	}

	public Chart<S> unload(final Scrip scrip) {
		return this;
	}

	protected String buildId(final Scrip scrip) {
		return null == scrip ? null : scrip.getIsin();
	}

	public Chart<S> showIndexInfo(final int index) {
		plots.forEach(plot -> plot.showIndexInfo(index));
		return this;
	}

	public Chart<S> reset() {
		plots.forEach(Plot::reset);
		clearDrawings();
		return this;
	}
	
	public boolean contains(final Plot<?,?> plot) {
		return plots.contains(plot);
	}

	public boolean isEmpty() {
		return plots.isEmpty();
	}

	public Chart<S> add(final DerivativePlot<?,?> plot) {
		if(plots.add(plot)) {
			plot.colorProperty().set(COLORS[plots.size()]);
			content.getChildren().add(plot.container());
			addPlotInfoPane(plot);
		}
		return this;
	}

	private Chart<S> addPlotInfoPane(final DerivativePlot<?,?> plot) {
		final PlotInfoPane plotInfoPane = plot.plotInfoPane();
		plotInfoPane.addVisibilityEventHandler(event -> plot.container().setVisible(!plot.container().isVisible()));
		plotInfoContainer.getChildren().add(plotInfoPane.getNode());
		plotInfoPane.addRemoveEventHandler(e -> remove(plot));
		return this;
	}

	public Chart<S> remove(final DerivativePlot<?,?> plot) {
		plots.remove(plot);
		content.getChildren().remove(plot.container());
		plotInfoContainer.getChildren().remove(plot.plotInfoPane().getNode());
		container.fireEvent(new PlotRemovedEvent(plot));
		return this;
	}
	
	public Chart<S> add(final Drawing<?> drawing) {
		if (drawings.add(drawing)) {
			Ui.fx(() -> content.getChildren().add(drawing.getNode()));
		}
		return this;
	}

	public Chart<S> remove(final Drawing<?> drawing) {
		drawings.remove(drawing);
		content.getChildren().remove(drawing.getNode());
		return this;
	}
	
	public Chart<S> clearDrawings() {
		while(!drawings.isEmpty()) {
			remove(drawings.iterator().next());
		}
		return this;
	}

	public Chart<S> updateValueBounds() {
		yAxis.reset();
		final int start = xAxis.getClippedStartIndex(), end = xAxis.getClippedEndIndex();
		plots.forEach(plot -> {
			plot.updateValueBounds(start, end);
			yAxis.setMin(Math.min(yAxis.getMin(), plot.min()));
			yAxis.setMax(Math.max(yAxis.getMax(), plot.max()));
		});
		return this;
	}

	public Chart<S> layoutChartChildren() {
		synchronized (volumeYAxis) {
			if (0 < content.getWidth() || 0 < content.getHeight()) {
				volumeYAxis.setDelegate(yAxis);
				plots.forEach(plot -> plot.layoutChartChildren(xAxis, Underlay.VOLUME.equals(plot.underlay()) ? volumeYAxis : yAxis));
				drawings.forEach(drawing -> drawing.layoutChartChildren(xAxis, yAxis));
				valueAxis.layoutChartChildren(yAxis);
			}
			return this;
		}
	}

}
