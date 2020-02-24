package com.stox.module.charting.chart;

import java.util.ArrayList;
import java.util.List;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.widget.NoLayoutPane;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.DelegatingYAxis;
import com.stox.module.charting.axis.vertical.MutableYAxis;
import com.stox.module.charting.axis.vertical.ValueAxis;
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
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Chart {
	private static final Color[] COLORS = { Color.BLUE, Color.BLUEVIOLET, Color.BROWN, Color.CRIMSON, Color.RED, Color.AQUA, Color.DARKGREEN };
	
	private ValueAxis valueAxis;
	
	private final XAxis xAxis;
	private final Configuration configuration;
	private final DelegatingYAxis volumeYAxis;
	
	private final VBox plotInfoContainer = new VBox();
	private final MutableYAxis yAxis = new MutableYAxis();
	private final List<DerivativePlot<?>> plots = new ArrayList<>();
	private final Pane content = new NoLayoutPane().classes("content");
	private final FluentBorderPane container = new FluentBorderPane(content).classes("chart").child(plotInfoContainer);

	protected Chart bind() {
		content.heightProperty().addListener((o, old, value) -> {
			yAxis.setHeight(value.doubleValue());
			updateValueBounds();
			layoutChartChildren();
		});
		container.addEventHandler(UpdatableRequestEvent.TYPE, event -> event.updatable().update(xAxis, yAxis));
		return this;
	}
	
	public Chart valueAxis(@NonNull ValueAxis valueAxis) {
		container.right(this.valueAxis = valueAxis);
		return this;
	}
	
	public Chart load(final Scrip scrip, final List<Bar> bars) {
		plots.forEach(plot -> plot.load(bars));
		return this;
	}

	public Chart unload(final Scrip scrip) {
		return this;
	}

	protected String buildId(final Scrip scrip) {
		return null == scrip ? null : scrip.getIsin();
	}

	public Chart showIndexInfo(final int index) {
		plots.forEach(plot -> plot.showIndexInfo(index));
		return this;
	}

	public Chart reset() {
		plots.forEach(Plot::reset);
		return this;
	}
	
	public boolean contains(final Plot<?> plot) {
		return plots.contains(plot);
	}

	public boolean isEmpty() {
		return plots.isEmpty();
	}

	public Chart add(final DerivativePlot<?> plot) {
		if (!plots.contains(plot)) {
			plot.colorProperty().set(COLORS[plots.size()]);
			content.getChildren().add(plot.container());
			addPlotInfoPane(plot);
			plots.add(plot);
		}
		return this;
	}

	private Chart addPlotInfoPane(final DerivativePlot<?> plot) {
		final PlotInfoPane plotInfoPane = plot.plotInfoPane();
		plotInfoPane.addVisibilityEventHandler(event -> plot.container().setVisible(!plot.container().isVisible()));
		plotInfoContainer.getChildren().add(plotInfoPane.getNode());
		plotInfoPane.addRemoveEventHandler(e -> remove(plot));
		return this;
	}

	public Chart remove(final DerivativePlot<?> plot) {
		plots.remove(plot);
		content.getChildren().remove(plot.container());
		plotInfoContainer.getChildren().remove(plot.plotInfoPane().getNode());
		container.fireEvent(new PlotRemovedEvent(plot));
		return this;
	}

	public Chart updateValueBounds() {
		yAxis.reset();
		final int start = xAxis.getClippedStartIndex(), end = xAxis.getClippedEndIndex();
		plots.forEach(plot -> {
			plot.updateValueBounds(start, end);
			yAxis.setMin(Math.min(yAxis.getMin(), plot.min()));
			yAxis.setMax(Math.max(yAxis.getMax(), plot.max()));
		});
		return this;
	}

	public Chart layoutChartChildren() {
		synchronized (volumeYAxis) {
			if (0 < content.getWidth() || 0 < content.getHeight()) {
				volumeYAxis.setDelegate(yAxis);
				plots.forEach(plot -> plot.layoutChartChildren(xAxis, Underlay.VOLUME.equals(plot.underlay()) ? volumeYAxis : yAxis));
				valueAxis.layoutChartChildren(yAxis);
			}
			return this;
		}
	}

}
