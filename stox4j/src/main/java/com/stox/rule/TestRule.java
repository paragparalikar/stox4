package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.ChainRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import org.ta4j.core.rules.helper.ChainLink;

import com.stox.indicator.StochasticOccilatorIndicator;
import com.stox.indicator.VolatilityContractionIndicator;
import com.stox.rule.SimpleBreakoutBarRule.SimpleBreakoutBarRuleConfig;

import lombok.Data;

public class TestRule extends AbstractRule {
	
	@Data
	public static class TestConfig {
		private int barCount = 34;
		private double maxStochasticKValue = 30;
		private double volatilityContractionMinValue = 0.5;
		private int insideBarThreshold = 5;
		private int breakoutBarCount = 5;
		private double breakoutBarMaxSpreadMultiple = 5;
		private double breakoutBarMaxHighDiffATRMultiple = 0.5;
		
		public SimpleBreakoutBarRuleConfig toSimpleBreakoutBarRuleConfig() {
			return new SimpleBreakoutBarRuleConfig(breakoutBarCount, breakoutBarMaxSpreadMultiple, breakoutBarMaxHighDiffATRMultiple);
		}
		public String toString() {return String.format("BarCount: %d", barCount);}
	}

	private final Rule delegate;
	
	public TestRule(BarSeries series, TestConfig config) {
		final SimpleBreakoutBarRule breakoutBarRule = new SimpleBreakoutBarRule(series, config.toSimpleBreakoutBarRuleConfig());
		
		final Indicator<Num> volatilityContractionIndicator = new VolatilityContractionIndicator(series, config.getBarCount());
		final Rule volatilityRule = new OverIndicatorRule(volatilityContractionIndicator, config.getVolatilityContractionMinValue());
		final ChainLink volatilityContractionChainLink = new ChainLink(volatilityRule, 2);
		
		final InsideBarRule insideBarRule = new InsideBarRule(series);
		final ChainLink insideBarChainLink = new ChainLink(insideBarRule, config.getInsideBarThreshold());
		
		final Indicator<Num> lowPriceIndicator = new LowPriceIndicator(series);
		final Indicator<Num> stochasticKIndicator = new StochasticOccilatorIndicator(lowPriceIndicator, 2 * config.getBarCount());
		final Rule oversoldRule = new UnderIndicatorRule(stochasticKIndicator, config.getMaxStochasticKValue());
		final ChainLink oversoldChainLink = new ChainLink(oversoldRule, 2);
		
		this.delegate = new ChainRule(breakoutBarRule, volatilityContractionChainLink);
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return delegate.isSatisfied(index, tradingRecord);
	}

}
