package com.stox.ml.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

public class BuyTradeClassIndicator extends CachedIndicator<Integer> {

	private final int barCount;
	private final int maxClass;
	private final double stepPercentage;
	private final Indicator<Num> highestHighIndicator;
	
	public BuyTradeClassIndicator(BarSeries series, int barCount, int maxClass, double stepPercentage) {
		super(series);
		this.maxClass = maxClass;
		this.barCount = barCount;
		this.stepPercentage = stepPercentage;
		final Indicator<Num> highPriceIndicator = new HighPriceIndicator(series);
		this.highestHighIndicator = new HighestValueIndicator(highPriceIndicator, barCount);
	}

	@Override
	protected Integer calculate(int index) {
		final BarSeries barSeries = getBarSeries();
		if(index < barSeries.getBarCount() - barCount - 1) {
			final Num close = getBarSeries().getBar(index).getClosePrice();
			final Num highestHigh = highestHighIndicator.getValue(index + barCount + 1);
			final Num step = close.multipliedBy(DoubleNum.valueOf(stepPercentage)).dividedBy(DoubleNum.valueOf(100));
			final Integer class_ = highestHigh.minus(close).dividedBy(step).ceil().intValue();
			return class_ > maxClass ? maxClass : class_;
		}
		return null;
	}

}
