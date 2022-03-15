package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;

import com.stox.charting.plot.rule.PlottableVolatilityContractionBreakoutRule.VolatilityContractionBreakoutRuleConfig;
import com.stox.indicator.RuleIndicator;
import com.stox.rule.VolatilityContractionBreakoutRule;

import lombok.Data;

public class PlottableVolatilityContractionBreakoutRule extends AbstractPlottableRule<VolatilityContractionBreakoutRuleConfig> {

	@Data
	public static class VolatilityContractionBreakoutRuleConfig {
		private int barCount = 34;
		private double minValue = 0.9;
	}
	
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
		final Rule rule = new VolatilityContractionBreakoutRule(barSeries, config.getBarCount(), config.getMinValue());
		return new RuleIndicator(rule, barSeries);
	}
	
}