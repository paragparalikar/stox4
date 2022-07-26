package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.DifferenceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.num.Num;

public class SpreadIndicator extends CachedIndicator<Num> {

	private final Indicator<Num> delegate;
	
	public SpreadIndicator(BarSeries series) {
		super(series);
		final LowPriceIndicator lowPriceIndicator = new LowPriceIndicator(series);
		final HighPriceIndicator highPriceIndicator = new HighPriceIndicator(series);
		this.delegate = new DifferenceIndicator(highPriceIndicator, lowPriceIndicator);
	}

	@Override
	protected Num calculate(int index) {
		return delegate.getValue(index);
	}

}
