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
import javafx.beans.property.ObjectProperty;
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
		private int fetchSize = 200;
		private double maxUnitWidthProperty = 50;
		private double minUnitWidthProperty = 1;
		private double paddingTopProperty = 10;
		private double paddingBottomProperty = 8;
	}
	
	@Getter
	public static class ChartingContext {
		private final ObjectProperty<Scrip> scripProperty = new SimpleObjectProperty<>();
		private final ObjectProperty<BarSeries> barSeriesProperty = new SimpleObjectProperty<>(new BaseBarSeries());
	}
	
	private final PricePlot pricePlot;
	private final ToolBar toolBar = new ToolBar();
	private final SplitPane splitPane = new SplitPane();
	private final ChartingConfig config = new ChartingConfig();
	private final VerticalGrid verticalGrid = new VerticalGrid();
	private final Crosshair crosshair = new Crosshair(splitPane);
	private final ChartingContext context = new ChartingContext();
	private final XAxis xAxis = new XAxis(context, config, crosshair, verticalGrid);
	private final Chart priceChart = new Chart(context, config, crosshair, xAxis);
	private final StackPane stackPane = new StackPane(verticalGrid, splitPane, crosshair);
	private final ObservableList<Chart> charts = FXCollections.observableArrayList();
	
	public ChartingView(BarService barService) {
		add(priceChart);
		add(pricePlot = new PricePlot(config, barService));
		
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
		charts.add(chart);
		splitPane.getItems().add(chart);
	}
	
	public void add(Plot<?> plot) {
		priceChart.add(plot);
	}
	
	public void setScrip(Scrip scrip) {
		context.getScripProperty().set(scrip);
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
