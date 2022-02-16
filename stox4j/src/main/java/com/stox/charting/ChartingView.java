package com.stox.charting;

import com.dlsc.workbenchfx.Workbench;
import com.stox.charting.axis.XAxis;
import com.stox.charting.chart.Chart;
import com.stox.charting.chart.PriceChart;
import com.stox.charting.handler.pan.PanRequestEvent;
import com.stox.charting.handler.zoom.ZoomRequestEvent;
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

	private final XAxis xAxis = new XAxis();
	private final ToolBar toolBar = new ToolBar();
	private final SplitPane splitPane = new SplitPane();
	private final ChartingContext context = new ChartingContext();
	private final ObservableList<Chart> charts = FXCollections.observableArrayList();
	
	public ChartingView(Workbench workbench, BarService barService) {
		final PricePlot pricePlot = new PricePlot(context, barService);
		final PriceChart priceChart = new PriceChart(context, pricePlot);
		add(priceChart);
		
		setCenter(splitPane);
		setBottom(new VBox(xAxis, toolBar));
		toolBar.getItems().add(new RulesButton(workbench, context));
		addEventHandler(PanRequestEvent.TYPE, this::pan);
		addEventHandler(ZoomRequestEvent.TYPE, this::zoom);
	}
	
	public void add(Chart chart) {
		charts.add(chart);
		splitPane.getItems().add(chart);
	}
	
	public void reload() {
		for(Chart chart : charts) {
			chart.reload(context.getScrip(), xAxis);
		}
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
