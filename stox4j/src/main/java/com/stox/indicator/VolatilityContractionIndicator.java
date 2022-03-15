package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.ChopIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

public class VolatilityContractionIndicator extends CachedIndicator<Num> {

	private final Indicator<Num> atrIndicator;
	private final Indicator<Num> chopIndicator;
	private final Indicator<Num> stdDevIndicator;
	
	public VolatilityContractionIndicator(BarSeries series, int barCount) {
		super(series);
		this.atrIndicator = new ATRIndicator(series, barCount);
		this.chopIndicator = new ChopIndicator(series, barCount, 100);
		this.stdDevIndicator = new StandardDeviationIndicator(new TypicalPriceIndicator(series), barCount);
	}

	@Override
	protected Num calculate(int index) {
		final Num chop = chopIndicator.getValue(index);
		if(null == chop) return null;
		final Num stdDev = stdDevIndicator.getValue(index);
		if(null == stdDev) return null;
		final Num atr = atrIndicator.getValue(index);
		if(null == atr) return null;
		
		return chop.dividedBy(stdDev.multipliedBy(atr));
	}

}
