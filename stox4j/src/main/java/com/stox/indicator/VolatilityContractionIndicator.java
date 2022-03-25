package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.ChopIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.DifferenceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.OpenPriceIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.SumIndicator;
import org.ta4j.core.indicators.helpers.TransformIndicator;
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
		final Indicator<Num> openPriceIndicator = new OpenPriceIndicator(series);
		final Indicator<Num> closePriceIndicator = new ClosePriceIndicator(series);
		final Indicator<Num> bodyIndicator = new DifferenceIndicator(closePriceIndicator, openPriceIndicator);
		final Indicator<Num> absBodyIndicator = new TransformIndicator(bodyIndicator, Num::abs);
		final Indicator<Num> highPriceIndicator = new HighPriceIndicator(series);
		final Indicator<Num> lowPriceIndicator = new LowPriceIndicator(series);
		final Indicator<Num> spreadIndicator = new DifferenceIndicator(highPriceIndicator, lowPriceIndicator);
		final Indicator<Num> previousCloseIndicator = new PreviousValueIndicator(closePriceIndicator);
		final Indicator<Num> changeIndicator = new DifferenceIndicator(closePriceIndicator, previousCloseIndicator);
		final Indicator<Num> absChangeIndicator = new TransformIndicator(changeIndicator, Num::abs);
		final Indicator<Num> stdDevIndicator = new StandardDeviationIndicator(new TypicalPriceIndicator(series), barCount);
		final Indicator<Num> sumIndicator = new SumIndicator(closePriceIndicator, absBodyIndicator, 
				spreadIndicator, absChangeIndicator, stdDevIndicator);

		final SMAIndicator smaIndicator = new SMAIndicator(sumIndicator, 5);
		final int scaleUpTo = series.getBar(index).getClosePrice().intValue();
		final Num chop = new ChopIndicator(series, barCount, scaleUpTo).getValue(index);
		return chop.dividedBy(smaIndicator.getValue(index));
	}

}
