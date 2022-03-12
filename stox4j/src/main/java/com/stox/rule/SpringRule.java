package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.Data;

public class SpringRule extends AbstractRule {
	
	@Data
	public static class SpringRuleConfig {
		private int barCount = 144;
	}

	private final Rule pivoteRule;
	private final BarSeries barSeries;
	private final SpringRuleConfig config;
	
	public SpringRule(BarSeries barSeries, SpringRuleConfig config) {
		this.config = config;
		this.barSeries = barSeries;
		this.pivoteRule = new LowPivoteRule(barSeries);
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		final int barCount = config.getBarCount();
		if(index < 2 || index < barCount || index >= barSeries.getBarCount()) return false;
		final Bar bar = barSeries.getBar(index);
		final Bar one = barSeries.getBar(index - 1);
		final Bar two = barSeries.getBar(index - 2);
		if(bar.getClosePrice().isLessThan(one.getClosePrice())) return false;
		if(two.getClosePrice().isLessThan(one.getClosePrice())) return false;
		for(int i = index - barCount; i < index - 1; i++) {
			if(pivoteRule.isSatisfied(i)) {
				final Bar pivote = barSeries.getBar(i);
				if(bar.getClosePrice().isGreaterThanOrEqual(pivote.getLowPrice()) &&
						one.getClosePrice().isLessThanOrEqual(pivote.getLowPrice()) &&
						two.getClosePrice().isGreaterThanOrEqual(pivote.getLowPrice())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
