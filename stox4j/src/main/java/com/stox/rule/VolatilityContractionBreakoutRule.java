package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.UnderIndicatorRule;

import com.stox.indicator.PVolatilityIndicator;
import com.stox.screener.ScreenerConfig;

import lombok.Data;
import lombok.experimental.Delegate;

public class VolatilityContractionBreakoutRule extends AbstractRule {
	
	@Data
	public static class VolatilityContractionBreakoutRuleConfig implements ScreenerConfig {
		private double maxPVolatility = 0.25;
		private int barCount = 21;

		public String toString() {return String.format("BarCount: %d", barCount);}
	}

	@Delegate private final Rule delegate;
	
	public VolatilityContractionBreakoutRule(BarSeries barSeries, int barCount, VolatilityContractionBreakoutRuleConfig config) {
		final Rule breakoutRule = new SimpleBreakoutBarRule(barSeries);
		final Indicator<Num> pVolatilityIndicator = new PVolatilityIndicator(barSeries, barCount);
		final Rule volatilityRule = new UnderIndicatorRule(pVolatilityIndicator, config.getMaxPVolatility());
		this.delegate = breakoutRule.and(volatilityRule);
	}

}
