package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.num.Num;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RuleIndicator implements Indicator<Boolean> {

	private final Rule rule;
	private final BarSeries barSeries;

	@Override
	public Boolean getValue(int index) {
		return rule.isSatisfied(index);
	}

	@Override
	public BarSeries getBarSeries() {
		return barSeries;
	}

	@Override
	public Num numOf(Number number) {
		return barSeries.numOf(number);
	}

}
