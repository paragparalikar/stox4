package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.CombineIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.num.Num;

public class HighPriceDeltaIndicator extends CachedIndicator<Num> {

	private final Indicator<Num> delegate;
	
	public HighPriceDeltaIndicator(BarSeries series, int barCount) {
		super(series);
		final HighPriceIndicator highPriceIndicator = new HighPriceIndicator(series);
		final SMAIndicator averageHighIndicator = new SMAIndicator(highPriceIndicator, barCount);
		final Indicator<Num> previousAvgHighIndicator = new PreviousValueIndicator(averageHighIndicator);
		this.delegate = CombineIndicator.minus(highPriceIndicator, previousAvgHighIndicator);
	}

	@Override
	protected Num calculate(int index) {
		return delegate.getValue(index);
	}

}
