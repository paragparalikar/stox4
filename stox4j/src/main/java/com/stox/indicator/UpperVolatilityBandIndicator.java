package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.num.Num;

public class UpperVolatilityBandIndicator extends CachedIndicator<Num> {
	
	private final Indicator<Num> delegate;

	public UpperVolatilityBandIndicator(BarSeries series, int barCount) {
		super(series);
		final Indicator<Num> typicalPriceIndicator = new TypicalPriceIndicator(series);
		final Indicator<Num> priceSpreadIndicator =  new PriceSpreadIndicator(series);
		final Indicator<Num> candleBodyIndicator = new CandleBodyIndicator(series);
		final Indicator<Num> closePriceIndicator = new ClosePriceIndicator(series);
		final Indicator<Num> changeIndicaot = new ChangeIndicator(closePriceIndicator, 1);
		final Indicator<Num> plusIndicator = new PlusIndicator(series, 
				typicalPriceIndicator, priceSpreadIndicator, candleBodyIndicator, changeIndicaot);
		this.delegate = new SMAIndicator(plusIndicator, barCount);
	}

	@Override
	protected Num calculate(int index) {
		return delegate.getValue(index);
	}
	
}
