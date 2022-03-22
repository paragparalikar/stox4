package com.stox.indicator;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

public class ChangeIndicator extends CachedIndicator<Num> {
	
	private final int barCount;
	private final Indicator<Num> indicator;
	
	public ChangeIndicator(Indicator<Num> indicator) {
		this(indicator, 1);
	}

	public ChangeIndicator(Indicator<Num> indicator, int barCount) {
		super(indicator.getBarSeries());
		this.barCount = barCount;
		this.indicator = indicator;
	}

	@Override
	protected Num calculate(int index) {
		final int nIndex = Math.max(index - barCount, 0);
		final Num nValue = indicator.getValue(nIndex);
		final Num value = indicator.getValue(index);
		return value.minus(nValue);
	}

}
