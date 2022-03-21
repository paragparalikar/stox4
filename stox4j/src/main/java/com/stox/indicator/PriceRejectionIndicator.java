package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

public class PriceRejectionIndicator extends CachedIndicator<Num> {

	public PriceRejectionIndicator(BarSeries series) {
		super(series);
	}

	@Override
	protected Num calculate(int index) {
		if(index < 1 || index > getBarSeries().getBarCount() - 2) return null;
		final double previousHigh = getBarSeries().getBar(index - 1).getHighPrice().doubleValue();
		final double currentLow = getBarSeries().getBar(index).getLowPrice().doubleValue();
		final double currentHigh = getBarSeries().getBar(index).getHighPrice().doubleValue();
		final double nextHigh = getBarSeries().getBar(index + 1).getHighPrice().doubleValue();
		final double rejection =( Math.max(previousHigh, currentHigh) - currentLow) + 
				(Math.max(nextHigh, currentHigh) - currentLow);
		return numOf(rejection * 100 / currentLow);
	}

}
