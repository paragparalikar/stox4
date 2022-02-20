package com.stox.charting.plot;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.common.ui.ModelAndView;

public interface PlotFacade<T> {
	
	double resolveLowValue(T model);
	double resolveHighValue(T model);
	ModelAndView createConfigView(); 
	Indicator<T> createIndicator(BarSeries barSeries);

}
