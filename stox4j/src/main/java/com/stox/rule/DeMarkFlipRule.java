package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeMarkFlipRule extends AbstractRule {

	private final BarSeries series;
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(5 < index && index < series.getBarCount()) {
			final Bar bar = series.getBar(index);
			final Bar one = series.getBar(index - 1);
			final Bar four = series.getBar(index - 4);
			final Bar five = series.getBar(index - 5);
			
			return bar.getClosePrice().isGreaterThanOrEqual(four.getClosePrice()) &&
					one.getClosePrice().isLessThanOrEqual(five.getClosePrice());
		}
		return false;
	}

}
