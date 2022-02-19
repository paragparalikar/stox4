package com.stox.charting;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.charting.axis.XAxis;
import com.stox.charting.grid.Crosshair;
import com.stox.charting.grid.VerticalGrid;
import com.stox.charting.handler.pan.PanRequestEvent;
import com.stox.charting.handler.zoom.ZoomRequestEvent;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.PricePlot;
import com.stox.charting.tools.RulesButton;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.Getter;

public class ChartingView extends BorderPane {
	
	@Getter
	public static class ChartingConfig {
		private final DoubleProperty maxUnitWidthProperty = new SimpleDoubleProperty(50);
		private final DoubleProperty minUnitWidthProperty = new SimpleDoubleProperty(1);
	}
	
	@Getter
	public static class ChartingContext {
		private final ObjectProperty<Scrip> scripProperty = new SimpleObjectProperty<>();
		private final ObjectProperty<BarSeries> barSeriesProperty = new SimpleObjectProperty<>(new BaseBarSeries());
	}

	private final XAxis xAxis;
	private final PricePlot pricePlot;
	private final ChartingConfig config;
	private final ChartingContext context;
	private final Chart priceChart = new Chart();
	private final ToolBar toolBar = new ToolBar();
	private final SplitPane splitPane = new SplitPane();
	private final Crosshair crosshair = new Crosshair(splitPane);
	private final VerticalGrid verticalGrid = new VerticalGrid();
	private final StackPane stackPane = new StackPane(verticalGrid, splitPane, crosshair);
	private final ObservableList<Chart> charts = FXCollections.observableArrayList();
	
	public ChartingView(ChartingContext context, ChartingConfig config, BarService barService) {
		this.config = config;
		this.context = context;
		this.xAxis = XAxis.builder().config(config).context(context).crosshair(crosshair)
				.verticalGrid(verticalGrid).build();
		
		add(priceChart);
		add(pricePlot = new PricePlot(barService));
		
		setCenter(stackPane);
		setBottom(new VBox(xAxis, toolBar));
		addEventHandler(PanRequestEvent.TYPE, this::pan);
		addEventHandler(ZoomRequestEvent.TYPE, this::zoom);
		toolBar.getItems().add(new RulesButton(this, context));
		context.getBarSeriesProperty().addListener((o,old,value) -> redraw());
		
		splitPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
	}
	
	public void add(Chart chart) {
		chart.setXAxis(xAxis);
		chart.setContext(context);
		charts.add(chart);
		splitPane.getItems().add(chart);
	}
	
	public void add(Plot<?> plot) {
		priceChart.add(plot);
	}
	
	public void pan(PanRequestEvent event) {
		xAxis.pan(event.getDeltaX());
		redraw();
		pricePlot.reloadBars();
	}
	
	public void zoom(ZoomRequestEvent event) {
		xAxis.zoom(event.getX(), event.getPercentage());
		redraw();
		pricePlot.reloadBars();
	}
	
	public void redraw() {
		if(Platform.isFxApplicationThread()) {
			priceChart.redraw();
			charts.forEach(Chart::redraw);
		} else {
			Platform.runLater(() -> {
				priceChart.redraw();
				charts.forEach(Chart::redraw);
			});
		}
	}
}
