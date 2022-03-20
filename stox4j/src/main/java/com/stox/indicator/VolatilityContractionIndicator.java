package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.ChopIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

public class VolatilityContractionIndicator extends CachedIndicator<Num> {

	private final int barCount;
	
	public VolatilityContractionIndicator(BarSeries series, int barCount) {
		super(series);
		this.barCount = barCount;
	}

	@Override
	protected Num calculate(int index) {
		final BarSeries series = getBarSeries();
		final PlusIndicator plusIndicator = new PlusIndicator(series,
				new ClosePriceIndicator(series), 
				new CandleBodyIndicator(series), 
				new PriceSpreadIndicator(series), 
				new ChangeIndicator(new ClosePriceIndicator(series), 1), 
				new StandardDeviationIndicator(new TypicalPriceIndicator(series), barCount));
		final SMAIndicator smaIndicator = new SMAIndicator(plusIndicator, 5);
		final int scaleUpTo = series.getBar(index).getClosePrice().intValue();
		final Num chop = new ChopIndicator(series, barCount, scaleUpTo).getValue(index);
		return chop.dividedBy(smaIndicator.getValue(index));
	}

}
