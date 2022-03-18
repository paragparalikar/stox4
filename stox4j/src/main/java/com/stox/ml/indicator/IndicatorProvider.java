package com.stox.ml.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

public interface IndicatorProvider<T, C> {

	Indicator<T> createIndicator(C config, BarSeries barSeries);
	
}
