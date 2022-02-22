package com.stox.rule;

import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.Data;

public class SmartBreakoutBarRule extends AbstractRule {

	@Data
	public static class SmartBreakoutBarRuleConfig {
		private int span = 5;
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return false;
	}

}
