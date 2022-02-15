package com.stox.charting;

import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.charting.unit.CandleUnit;
import com.stox.charting.unit.resolver.BarHighLowResolver;
import com.stox.common.bar.Bar;
import com.stox.common.bar.BarRepository;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.BarIndicator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class ChartingView extends BorderPane {

	private Scrip scrip;
	
	private final BarRepository barRepository;
	private final BarIndicator barIndicator = new BarIndicator();
	private final Plot<Bar> barPlot = new Plot<>(barIndicator, CandleUnit::new, new BarHighLowResolver());
	private final Chart barChart = new Chart(barPlot);
	private final ObservableList<Chart> charts = FXCollections.observableArrayList(barChart);
	private final ChartingViewContentArea splitPane = new ChartingViewContentArea(charts);
	
	public ChartingView(BarRepository barRepository) {
		this.barRepository = barRepository;
		setCenter(splitPane);
	}
	
	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
		final List bars = barRepository.find(scrip.getIsin(), 200);
		final BarSeries barSeries = new BaseBarSeries(bars);
		barIndicator.setBarSeries(barSeries);
		splitPane.layoutCharts();
	}
	
}
