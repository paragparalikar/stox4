package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.OverIndicatorRule;

import com.stox.indicator.VolatilityContractionIndicator;

import lombok.experimental.Delegate;

public class VolatilityContractionBreakoutRule extends AbstractRule {

	@Delegate private final Rule delegate;
	
	public VolatilityContractionBreakoutRule(BarSeries barSeries, int barCount, double minValue) {
		final Rule breakoutRule = new SimpleBreakoutBarRule(barSeries);
		final Indicator<Num> volatilityContractionIndicator = new VolatilityContractionIndicator(barSeries, barCount);
		final Indicator<Num> stochasticIndicator = new StochasticRSIIndicator(volatilityContractionIndicator, barCount);
		final Rule volatilityRule = new OverIndicatorRule(stochasticIndicator, minValue);
		this.delegate = breakoutRule.and(volatilityRule);
	}

}
