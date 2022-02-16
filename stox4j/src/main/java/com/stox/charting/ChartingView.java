package com.stox.charting;

import org.ta4j.core.BaseBarSeries;

import com.stox.charting.axis.XAxis;
import com.stox.charting.handler.pan.PanRequestEvent;
import com.stox.charting.handler.zoom.ZoomRequestEvent;
import com.stox.charting.price.PriceChart;
import com.stox.charting.price.PricePlot;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.BarIndicator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ChartingView extends BorderPane {

	private Scrip scrip;
	private final XAxis xAxis = new XAxis();
	private final SplitPane splitPane = new SplitPane();
	private final ToolBar toolBar = new ToolBar(new Button("Hello"));
	private final BarIndicator barIndicator = new BarIndicator();
	private final ObservableList<Chart> charts = FXCollections.observableArrayList();
	
	public ChartingView(BarService barService) {
		setCenter(splitPane);
		setBottom(new VBox(xAxis, toolBar));
		addEventHandler(PanRequestEvent.TYPE, this::pan);
		addEventHandler(ZoomRequestEvent.TYPE, this::zoom);
		
		final PricePlot pricePlot = new PricePlot(barIndicator, barService);
		final PriceChart priceChart = new PriceChart(pricePlot);
		add(priceChart);
	}
	
	public void add(Chart chart) {
		charts.add(chart);
		splitPane.getItems().add(chart);
	}
	
	public void reload() {
		for(Chart chart : charts) chart.reload(scrip, xAxis);
	}
	
	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
		barIndicator.setBarSeries(new BaseBarSeries());
		xAxis.resetPanWidth();
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
