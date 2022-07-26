package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BullishBarRule extends AbstractRule {
	
	private final BarSeries barSeries;
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(index < 2 || index >= barSeries.getBarCount()) return false;
		final Bar bar = barSeries.getBar(index);
		final Bar one = barSeries.getBar(index - 1);
		final Bar two = barSeries.getBar(index - 2);
		return bar.getClosePrice().isGreaterThan(one.getHighPrice()) &&
				one.getClosePrice().isLessThan(two.getHighPrice());
	}
	
}