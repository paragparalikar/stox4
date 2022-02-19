package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestRule extends AbstractRule {
	
	private final BarSeries barSeries;

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return index % 3 == 0;
	}
	
}
