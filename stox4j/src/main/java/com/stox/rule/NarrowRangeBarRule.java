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
import org.ta4j.core.rules.IsLowestRule;

import com.stox.screener.ScreenerConfig;

import lombok.Data;

public class NarrowRangeBarRule extends AbstractRule {
	
	@Data
	public static class NarrowRangeBarConfig implements ScreenerConfig {
		private int barCount = 7;
		public String toString() { return "barCount = " + barCount; };
	}
	
	private final Rule delegate;
	
	public NarrowRangeBarRule(BarSeries series, NarrowRangeBarConfig config) {
		final HighPriceIndicator highPriceIndicator = new HighPriceIndicator(series);
		final LowPriceIndicator lowPriceIndicator = new LowPriceIndicator(series);
		final Indicator<Num> spreadIndicator = CombineIndicator.minus(highPriceIndicator, lowPriceIndicator);
		final IsLowestRule isLowestRule = new IsLowestRule(spreadIndicator, config.getBarCount());
		this.delegate = isLowestRule;
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return delegate.isSatisfied(index, tradingRecord);
	}
	
}
