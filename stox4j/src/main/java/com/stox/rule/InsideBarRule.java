package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InsideBarRule extends AbstractRule {

	private final BarSeries series;
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(index < series.getRemovedBarsCount() + 1) return false;
		if(index >= series.getBarCount()) return false;
		final Bar bar = series.getBar(index);
		final Bar one = series.getBar(index - 1);
		if(bar.getHighPrice().isGreaterThan(one.getHighPrice())) return false;
		if(bar.getLowPrice().isLessThan(one.getLowPrice())) return false;
		return true;
	}

}
