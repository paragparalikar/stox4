package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleBreakoutBarRule extends AbstractRule {
	
	public static class SimpleBreakoutBarRuleConfig {
		
	}

	@NonNull private final BarSeries barSeries;

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(null != barSeries && 0 <= index && index < barSeries.getBarCount() - 2) {
			final Bar bar = barSeries.getBar(index);
			final Bar one = barSeries.getBar(index + 1);
			final Bar two = barSeries.getBar(index + 2);
			if(bar.getClosePrice().isLessThan(one.getHighPrice())) return false;
			if(one.getClosePrice().isGreaterThan(two.getHighPrice())) return false;
			return true;
		}
		return false;
	}

}
