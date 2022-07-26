package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.num.Num;

public class ATRRatioIndicator extends CachedIndicator<Num> {

	private final Indicator<Num> delegate;
	
	public ATRRatioIndicator(BarSeries series, int barCount) {
		super(series);
		final TypicalPriceIndicator typicalPriceIndicator = new TypicalPriceIndicator(series);
		final ATRIndicator atrIndicator = new ATRIndicator(series, barCount);
		final PreviousValueIndicator prevATRIndicator = new PreviousValueIndicator(atrIndicator);
		this.delegate = new RatioIndicator(typicalPriceIndicator, prevATRIndicator);
	}

	@Override
	protected Num calculate(int index) {
		return delegate.getValue(index);
	}

}
