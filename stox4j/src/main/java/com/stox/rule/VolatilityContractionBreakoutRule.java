package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.OverIndicatorRule;

import com.stox.indicator.VolatilityContractionIndicator;
import com.stox.rule.SimpleBreakoutBarRule.SimpleBreakoutBarRuleConfig;
import com.stox.screener.ScreenerConfig;

import lombok.Data;
import lombok.experimental.Delegate;

public class VolatilityContractionBreakoutRule extends AbstractRule {
	
	@Data
	public static class VolatilityContractionBreakoutRuleConfig implements ScreenerConfig {
		private double minValue = 0.5;
		private int barCount = 55;
		private int breakoutBarCount = 5;
		private double spreadMultiple = 5;
		private double highDiffATRMultiple = 0.5;
		
		public SimpleBreakoutBarRuleConfig toBreakoutBarConfig() {
			return new SimpleBreakoutBarRuleConfig(breakoutBarCount, spreadMultiple, highDiffATRMultiple);
		}
		public String toString() {return String.format("BarCount: %d", barCount);}
	}

	@Delegate private final Rule delegate;
	
	public VolatilityContractionBreakoutRule(BarSeries barSeries, int barCount, VolatilityContractionBreakoutRuleConfig config) {
		final Rule breakoutRule = new SimpleBreakoutBarRule(barSeries, config.toBreakoutBarConfig());
		final Indicator<Num> volatilityContractionIndicator = new VolatilityContractionIndicator(barSeries, barCount);
		final Rule volatilityRule = new OverIndicatorRule(volatilityContractionIndicator, config.getMinValue());
		this.delegate = breakoutRule.and(volatilityRule);
	}

}
