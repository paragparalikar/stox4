package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

public class VolatilityIndicator extends CachedIndicator<Num> {

	private final SMAIndicator smaIndicator;
	private final TypicalPriceIndicator typicalPriceIndicator;
	private final StandardDeviationIndicator standardDeviationIndicator;
	
	public VolatilityIndicator(BarSeries series, int barCount) {
		super(series);
		this.typicalPriceIndicator = new TypicalPriceIndicator(series);
		this.standardDeviationIndicator = new StandardDeviationIndicator(typicalPriceIndicator, barCount);
		this.smaIndicator = new SMAIndicator(typicalPriceIndicator, barCount);
	}

	@Override
	protected Num calculate(int index) {
		return standardDeviationIndicator.getValue(index)
				.multipliedBy(smaIndicator.numOf(100)).dividedBy(smaIndicator.getValue(index));
	}

}
