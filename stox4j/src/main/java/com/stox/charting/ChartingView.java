package com.stox.charting;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

import com.stox.charting.axis.XAxis;
import com.stox.charting.event.PanRequestEvent;
import com.stox.charting.event.ZoomRequestEvent;
import com.stox.charting.unit.CandleUnit;
import com.stox.charting.unit.resolver.BarHighLowResolver;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.BarIndicator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ChartingView extends BorderPane {
	private static final int INITIAL_BAR_COUNT = 200;
	private static final int FETCH_BAR_COUNT = 200;

	private final BarService barService;
	private final XAxis xAxis = new XAxis();
	private final ToolBar toolBar = new ToolBar(new Button("Hello"));
	private final BarIndicator barIndicator = new BarIndicator();
	private final Plot<Bar> barPlot = new Plot<>(barIndicator, CandleUnit::new, new BarHighLowResolver());
	private final Chart barChart = new Chart(barPlot);
	private final ObservableList<Chart> charts = FXCollections.observableArrayList(barChart);
	private final ChartingViewContentArea splitPane = new ChartingViewContentArea(charts);
	
	public ChartingView(BarService barService) {
		this.barService = barService;
		setCenter(splitPane);
		setBottom(new VBox(xAxis, toolBar));
		addEventHandler(PanRequestEvent.TYPE, this::pan);
		addEventHandler(ZoomRequestEvent.TYPE, this::zoom);
	}
	
	public void redraw() {
		splitPane.layoutCharts(xAxis);
	}
	
	public void setScrip(Scrip scrip) {
		final BarSeries barSeries = barService.find(scrip.getIsin(), INITIAL_BAR_COUNT);
		barIndicator.setBarSeries(barSeries);
		redraw();
	}
	
	private void pan(PanRequestEvent event) {
		xAxis.pan(event.getDeltaX());
		redraw();
	}
	
	private void zoom(ZoomRequestEvent event) {
		xAxis.zoom(event.getX(), event.getPercentage());
		redraw();
	}
	
}
