package com.stox.charting.tools;

import java.util.function.Function;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ModelAndView;

import lombok.Value;

@Value
public class RuleBuilder {
	
	private final String name;
	private final ModelAndView configModelAndView;
	private final Function<BarSeries, Rule> function;
	
	public Rule build(BarSeries barSeries) {
		return function.apply(barSeries);
	}
	
	@Override
	public String toString() {
		return name;
	}
}