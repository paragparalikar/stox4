package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.num.Num;

public class SpreadRatioIndicator extends CachedIndicator<Num> {

	private final Indicator<Num> delegate;
	
	public SpreadRatioIndicator(BarSeries series, int barCount) {
		super(series);
		final Indicator<Num> spreadIndicator = new SpreadIndicator(series);
		final Indicator<Num> averageSpreadIndicator = new SMAIndicator(spreadIndicator, barCount);
		final Indicator<Num> previousAvgSpreadIndicator = new PreviousValueIndicator(averageSpreadIndicator);
		this.delegate = new RatioIndicator(spreadIndicator, previousAvgSpreadIndicator);
	}

	@Override
	protected Num calculate(int index) {
		return delegate.getValue(index);
	}

}
