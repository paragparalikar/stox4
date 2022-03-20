package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;

import com.stox.indicator.RuleIndicator;
import com.stox.rule.VolatilityContractionBreakoutRule;
import com.stox.rule.VolatilityContractionBreakoutRule.VolatilityContractionBreakoutRuleConfig;

public class PlottableVolatilityContractionBreakoutRule extends AbstractPlottableRule<VolatilityContractionBreakoutRuleConfig> {
	
	@Override
	public String toString() {
		return "Volatility Contraction Breakout";
	}

	@Override
	public VolatilityContractionBreakoutRuleConfig createConfig() {
		return new VolatilityContractionBreakoutRuleConfig();
	}

	@Override
	public Indicator<Boolean> createIndicator(VolatilityContractionBreakoutRuleConfig config, BarSeries barSeries) {
		final Rule rule = new VolatilityContractionBreakoutRule(barSeries, config.getBarCount(), config);
		return new RuleIndicator(rule, barSeries);
	}
	
}
