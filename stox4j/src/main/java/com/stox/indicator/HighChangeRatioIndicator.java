package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.CombineIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.TransformIndicator;
import org.ta4j.core.num.Num;

public class HighChangeRatioIndicator extends AbstractIndicator<Num> {

	private final Indicator<Num> delegate;
	
	public HighChangeRatioIndicator(BarSeries series, int barCount) {
		super(series);
		final Indicator<Num> highPriceIndicator = new HighPriceIndicator(series);
		final Indicator<Num> previousHighIndicator = new PreviousValueIndicator(highPriceIndicator);
		final Indicator<Num> highChangeIndicator = CombineIndicator.minus(highPriceIndicator, previousHighIndicator);
		final Indicator<Num> absHighChangeIndicator = TransformIndicator.abs(highChangeIndicator);
		final Indicator<Num> avgAbsHighChangeIndicator = new SMAIndicator(absHighChangeIndicator, barCount);
		final Indicator<Num> prevAvgAbsHighChangeIndicator = new PreviousValueIndicator(avgAbsHighChangeIndicator);
		this.delegate = new RatioIndicator(absHighChangeIndicator, prevAvgAbsHighChangeIndicator);
	}

	@Override
	public Num getValue(int index) {
		return delegate.getValue(index);
	}


}
