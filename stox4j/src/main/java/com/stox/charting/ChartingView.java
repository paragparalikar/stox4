package com.stox.charting;

import com.stox.charting.axis.XAxis;
import com.stox.charting.chart.Chart;
import com.stox.charting.chart.PriceChart;
import com.stox.charting.handler.pan.PanRequestEvent;
import com.stox.charting.handler.zoom.ZoomRequestEvent;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.PricePlot;
import com.stox.charting.tools.RulesButton;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ChartingView extends BorderPane {

	private final PricePlot pricePlot;
	private final PriceChart priceChart;
	private final XAxis xAxis = new XAxis();
	private final ToolBar toolBar = new ToolBar();
	private final SplitPane splitPane = new SplitPane();
	private final ChartingContext context = new ChartingContext();
	private final ObservableList<Chart> charts = FXCollections.observableArrayList();
	
	public ChartingView(BarService barService) {
		pricePlot = new PricePlot(context, barService);
		priceChart = new PriceChart(context, pricePlot);
		splitPane.getItems().add(priceChart);
		
		setCenter(splitPane);
		setBottom(new VBox(xAxis, toolBar));
		toolBar.getItems().add(new RulesButton(this, context));
		addEventHandler(PanRequestEvent.TYPE, this::pan);
		addEventHandler(ZoomRequestEvent.TYPE, this::zoom);
	}
	
	public void add(Chart chart) {
		charts.add(chart);
		splitPane.getItems().add(chart);
	}
	
	public void add(Plot<?> plot) {
		priceChart.add(plot);
		reload();
	}
	
	public void reload() {
		final Scrip scrip = context.getScrip();
		priceChart.reload(scrip, xAxis);
		for(Chart chart : charts) chart.reload(scrip, xAxis);
	}
	
	public void setScrip(Scrip scrip) {
		context.setScrip(scrip);
		xAxis.reset();
		reload();
	}
	
	private void pan(PanRequestEvent event) {
		xAxis.pan(event.getDeltaX());
		reload();
	}
	
	private void zoom(ZoomRequestEvent event) {
		xAxis.zoom(event.getX(), event.getPercentage());
		reload();
	}
	
}
