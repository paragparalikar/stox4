package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LowPivoteRule extends AbstractRule {
	
	private final BarSeries barSeries;

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(index < 1 || index >= barSeries.getBarCount() - 1) return false;
		final Bar current = barSeries.getBar(index);
		final Bar previous = barSeries.getBar(index - 1);
		final Bar next = barSeries.getBar(index + 1);
		return previous.getLowPrice().isGreaterThan(current.getLowPrice()) &&
				next.getLowPrice().isGreaterThan(current.getLowPrice());
	}

}
