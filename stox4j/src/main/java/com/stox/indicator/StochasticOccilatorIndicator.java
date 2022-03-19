package com.stox.indicator;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;
import org.ta4j.core.num.Num;

public class StochasticOccilatorIndicator extends CachedIndicator<Num> {
	
	private final Indicator<Num> indicator;
	private final LowestValueIndicator lowestValueIndicator;
	private final HighestValueIndicator highestValueIndicator;

	public StochasticOccilatorIndicator(Indicator<Num> indicator, int barCount) {
		super(indicator);
		this.indicator = indicator;
		this.lowestValueIndicator = new LowestValueIndicator(indicator, barCount);
		this.highestValueIndicator = new HighestValueIndicator(indicator, barCount);
	}

	@Override
	protected Num calculate(int index) {
		final Num lowestLowPrice = lowestValueIndicator.getValue(index);
		final Num highestHighPrice = highestValueIndicator.getValue(index);
        return indicator.getValue(index).minus(lowestLowPrice).dividedBy(highestHighPrice.minus(lowestLowPrice))
                .multipliedBy(numOf(100));
	}

}
