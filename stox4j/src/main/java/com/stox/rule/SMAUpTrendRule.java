package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.OverIndicatorRule;

public class SMAUpTrendRule extends AbstractRule {
	
	private final Rule delegate;
	
	public SMAUpTrendRule(BarSeries series, int barCount) {
		final ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		final SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator, barCount);
		this.delegate = new OverIndicatorRule(closePriceIndicator, smaIndicator);
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return delegate.isSatisfied(index, tradingRecord);
	}

}
