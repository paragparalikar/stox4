package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.indicator.RuleIndicator;
import com.stox.rule.NarrowRangeBarRule;
import com.stox.rule.NarrowRangeBarRule.NarrowRangeBarConfig;

public class PlottableNarrowRangeBarRule extends AbstractPlottableRule<NarrowRangeBarConfig> {

	@Override
	public String toString() {
		return "Narrow Range Bar";
	}
	
	@Override
	public NarrowRangeBarConfig createConfig() {
		return new NarrowRangeBarConfig();
	}

	@Override
	public Indicator<Boolean> createIndicator(NarrowRangeBarConfig config, BarSeries barSeries) {
		return new RuleIndicator(new NarrowRangeBarRule(barSeries, config), barSeries);
	}

}
