package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.CombineIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.IsFallingRule;

import lombok.Data;

public class TestRule extends AbstractRule {
	
	@Data
	public static class TestConfig {
		private int barCount = 3;
		private double minStrength = 1.0;
	}
	
	private final Rule delegate;

	public TestRule(BarSeries series, TestConfig config) {
		final HighPriceIndicator highPriceIndicator = new HighPriceIndicator(series);
		final LowPriceIndicator lowPriceIndicator = new LowPriceIndicator(series);
		final Indicator<Num> spreadIndicator = CombineIndicator.minus(highPriceIndicator, lowPriceIndicator);
		final IsFallingRule isFallingRule = new IsFallingRule(spreadIndicator, config.getBarCount(), config.getMinStrength());
		this.delegate = isFallingRule;
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return delegate.isSatisfied(index, tradingRecord);
	}

}
