package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.num.Num;

public class SMARatioIndicator extends CachedIndicator<Num> {
	
	private final Indicator<Num> delegate;

	public SMARatioIndicator(BarSeries series, int barCount) {
		super(series);
		final TypicalPriceIndicator typicalPriceIndicator = new TypicalPriceIndicator(series);
		final SMAIndicator smaIndicator = new SMAIndicator(typicalPriceIndicator, barCount);
		this.delegate = new RatioIndicator(typicalPriceIndicator, smaIndicator);
	}

	@Override
	protected Num calculate(int index) {
		return delegate.getValue(index);
	}

}
