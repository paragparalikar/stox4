package com.stox.charting;

import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
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
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ChartingView extends BorderPane {
	private static final int INITIAL_BAR_COUNT = 200;
	private static final int FETCH_BAR_COUNT = 100;

	private Scrip scrip;
	private final BarService barService;
	private final XAxis xAxis = new XAxis();
	private final SplitPane splitPane = new SplitPane();
	private final ToolBar toolBar = new ToolBar(new Button("Hello"));
	private final BarIndicator barIndicator = new BarIndicator();
	private final PricePlot pricePlot = new PricePlot(barIndicator);
	private final PriceChart priceChart = new PriceChart(pricePlot);
	private final ObservableList<Chart> charts = FXCollections.observableArrayList();
	
	public ChartingView(BarService barService) {
		this.barService = barService;
		setCenter(splitPane);
		setBottom(new VBox(xAxis, toolBar));
		charts.addListener(this::onChartsChanged);
		charts.add(priceChart);
		addEventHandler(PanRequestEvent.TYPE, this::pan);
		addEventHandler(ZoomRequestEvent.TYPE, this::zoom);
	}
	
	public void onChartsChanged(Change<? extends Chart> change) {
		splitPane.getItems().setAll(charts);
	}
	
	public void redraw() {
		for(Chart chart : charts) {
			chart.layoutChildren(xAxis);
		}
	}
	
	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
		final List<Bar> bars = barService.find(scrip.getIsin(), INITIAL_BAR_COUNT);
		barIndicator.setBarSeries(new BaseBarSeries(bars));
		xAxis.resetPanWidth();
		redraw();
	}
	
	private void pan(PanRequestEvent event) {
		xAxis.pan(event.getDeltaX());
		data();
		redraw();
	}
	
	private void zoom(ZoomRequestEvent event) {
		xAxis.zoom(event.getX(), event.getPercentage());
		data();
		redraw();
	}
	
	private void data() {
		final BarSeries barSeries = barIndicator.getBarSeries();
		if(xAxis.getEndIndex() > barSeries.getBarCount()) {
			final int gap = xAxis.getEndIndex() - barSeries.getBarCount();
			final int count = Math.max(gap, FETCH_BAR_COUNT);
			final List<Bar> bars = barService.find(scrip.getIsin(), count, barSeries.getLastBar().getEndTime());
			final List<Bar> data = barSeries.getBarData();
			data.addAll(bars);
			barIndicator.setBarSeries(new BaseBarSeries(data));
		}
	}
	
}
