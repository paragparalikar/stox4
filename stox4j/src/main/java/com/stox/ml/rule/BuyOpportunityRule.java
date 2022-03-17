package com.stox.ml.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

public class BuyOpportunityRule extends AbstractRule {

	private final int barCount;
	private final BarSeries barSeries;
	private final Indicator<Num> lowestValueIndicator;
	
	public BuyOpportunityRule(BarSeries barSeries, int barCount) {
		this.barCount = barCount;
		this.barSeries = barSeries;
		final LowPriceIndicator lowIndicator = new LowPriceIndicator(barSeries);
		lowestValueIndicator = new LowestValueIndicator(lowIndicator, barCount);
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(index < barSeries.getBarCount() - barCount) {
			final Num lowestLow = lowestValueIndicator.getValue(index + barCount);
			return barSeries.getBar(index).getClosePrice().isGreaterThanOrEqual(lowestLow);
		}
		return false;
	}

}
