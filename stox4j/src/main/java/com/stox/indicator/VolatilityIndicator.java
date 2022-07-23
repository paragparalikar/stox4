package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

public class VolatilityIndicator extends CachedIndicator<Num> {

	private final ATRIndicator atrIndicator;
	private final EMAIndicator emaIndicator;
	private final TypicalPriceIndicator typicalPriceIndicator;
	private final StandardDeviationIndicator standardDeviationIndicator;
	
	public VolatilityIndicator(BarSeries series, int barCount) {
		super(series);
		this.atrIndicator = new ATRIndicator(series, barCount);
		this.typicalPriceIndicator = new TypicalPriceIndicator(series);
		this.standardDeviationIndicator = new StandardDeviationIndicator(typicalPriceIndicator, barCount);
		this.emaIndicator = new EMAIndicator(typicalPriceIndicator, barCount);
	}

	@Override
	protected Num calculate(int index) {
		return atrIndicator.getValue(index).plus(standardDeviationIndicator.getValue(index))
				.multipliedBy(emaIndicator.numOf(100)).dividedBy(emaIndicator.getValue(index));
	}

}
