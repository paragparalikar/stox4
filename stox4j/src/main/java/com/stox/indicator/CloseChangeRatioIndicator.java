package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.CombineIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.TransformIndicator;
import org.ta4j.core.num.Num;

public class CloseChangeRatioIndicator extends AbstractIndicator<Num> {

	private final Indicator<Num> delegate;
	
	public CloseChangeRatioIndicator(BarSeries series, int barCount) {
		super(series);
		final ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		final Indicator<Num> previousCloseIndicator = new PreviousValueIndicator(closePriceIndicator);
		final Indicator<Num> closeChangeIndicator = CombineIndicator.minus(closePriceIndicator, previousCloseIndicator);
		final Indicator<Num> absCloseChangeIndicator = TransformIndicator.abs(closeChangeIndicator);
		final Indicator<Num> avgAbsCloseChangeIndicator = new SMAIndicator(absCloseChangeIndicator, barCount);
		final Indicator<Num> prevAvgAbsCloseChangeIndicator = new PreviousValueIndicator(avgAbsCloseChangeIndicator);
		this.delegate = new RatioIndicator(absCloseChangeIndicator, prevAvgAbsCloseChangeIndicator);
	}

	@Override
	public Num getValue(int index) {
		return delegate.getValue(index);
	}

}
