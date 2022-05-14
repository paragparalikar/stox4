package com.stox.charting.plot;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.charting.ChartingView;
import com.stox.charting.chart.Chart;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;

public interface Plottable<T, C, N> {
	
	double resolveLowValue(T model);
	double resolveHighValue(T model);
	
	Unit<T, N> createUnit();
	UnitParent<N> createUnitParent();
	
	C createConfig();
	ConfigView createConfigView(C config); 
	
	Indicator<T> createIndicator(C config, BarSeries barSeries);
	
	default void add(ChartingView chartingView) {
		final Chart chart = new Chart(chartingView);
		chartingView.add(chart);
		chart.add(new Plot<T, C, N>(this));
	}

}
