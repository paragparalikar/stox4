package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.TRIndicator;
import org.ta4j.core.num.Num;

public class TRRatioIndicator extends CachedIndicator<Num> {

	private final Indicator<Num> delegate;
	
	public TRRatioIndicator(BarSeries series, int barCount) {
		super(series);
		final TRIndicator trIndicator = new TRIndicator(series);
		final ATRIndicator atrIndicator = new ATRIndicator(series, barCount);
		final Indicator<Num> previousATRIndicator = new PreviousValueIndicator(atrIndicator);
		this.delegate = new RatioIndicator(trIndicator, previousATRIndicator);
	}

	@Override
	protected Num calculate(int index) {
		return delegate.getValue(index);
	}

}
