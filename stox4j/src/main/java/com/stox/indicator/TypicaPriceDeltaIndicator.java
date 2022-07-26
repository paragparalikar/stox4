package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.DifferenceIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.num.Num;

public class TypicaPriceDeltaIndicator extends CachedIndicator<Num> {

	private final Indicator<Num> delegate;
	
	public TypicaPriceDeltaIndicator(BarSeries series, int barCount) {
		super(series);
		final TypicalPriceIndicator typicalPriceIndicator = new TypicalPriceIndicator(series);
		final SMAIndicator averageTypicalPriceIndicator = new SMAIndicator(typicalPriceIndicator, barCount);
		final Indicator<Num> previousAvgTPIndicator = new PreviousValueIndicator(averageTypicalPriceIndicator);
		this.delegate = new DifferenceIndicator(typicalPriceIndicator, previousAvgTPIndicator);
	}

	@Override
	protected Num calculate(int index) {
		return delegate.getValue(index);
	}

}
