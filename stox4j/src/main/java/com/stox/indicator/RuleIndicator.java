package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.CachedIndicator;

public class RuleIndicator extends CachedIndicator<Boolean> {

	private final Rule rule;
	
	public RuleIndicator(Rule rule, BarSeries barSeries) {
		super(barSeries);
		this.rule = rule;
	}
	
	@Override
	protected Boolean calculate(int index) {
		return rule.isSatisfied(index);
	}

}
