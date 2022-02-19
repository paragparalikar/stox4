package com.stox.charting.plot;

import java.util.function.Function;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.common.ui.ModelAndView;

import lombok.Value;

@Value
public class PlotBuilder<T> {
	
	private final String name;
	private final ModelAndView configModelAndView;
	private final Function<BarSeries, Indicator<T>> function;
	
	public Indicator<T> build(BarSeries barSeries) {
		return function.apply(barSeries);
	}
	
	@Override
	public String toString() {
		return name;
	}
}