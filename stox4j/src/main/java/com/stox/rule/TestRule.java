package com.stox.rule;

import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

public class TestRule extends AbstractRule {

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return index % 3 == 0;
	}

	@Override
	public String toString() {
		return "Test";
	}
	
}
