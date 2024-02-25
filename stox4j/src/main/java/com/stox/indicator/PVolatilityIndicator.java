package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.ChopIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.candles.RealBodyIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.CombineIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.SumIndicator;
import org.ta4j.core.indicators.helpers.TransformIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

public class PVolatilityIndicator extends CachedIndicator<Num> {

	private final int barCount;
	private final Indicator<Num> smaIndicator;
	
	public PVolatilityIndicator(BarSeries series, int barCount) {
		super(series);
		this.barCount = barCount;
		final Indicator<Num> closePriceIndicator = new ClosePriceIndicator(series);
		final Indicator<Num> absBodyIndicator = TransformIndicator.abs(new RealBodyIndicator(series));
		final Indicator<Num> highPriceIndicator = new HighPriceIndicator(series);
		final Indicator<Num> lowPriceIndicator = new LowPriceIndicator(series);
		final Indicator<Num> spreadIndicator = CombineIndicator.minus(highPriceIndicator, lowPriceIndicator);
		final Indicator<Num> previousCloseIndicator = new PreviousValueIndicator(closePriceIndicator);
		final Indicator<Num> changeIndicator = CombineIndicator.minus(closePriceIndicator, previousCloseIndicator);
		final Indicator<Num> absChangeIndicator = TransformIndicator.abs(changeIndicator);
		final Indicator<Num> stdDevIndicator = new StandardDeviationIndicator(new TypicalPriceIndicator(series), barCount);
		final Indicator<Num> sumIndicator = new SumIndicator(absBodyIndicator, spreadIndicator, absChangeIndicator, stdDevIndicator);
		this.smaIndicator = new SMAIndicator(sumIndicator, 5);
	}

	@Override
	protected Num calculate(int index) {
		final BarSeries series = getBarSeries();
		final int scaleUpTo = series.getBar(index).getClosePrice().intValue();
		final Num chop = new ChopIndicator(series, barCount, scaleUpTo).getValue(index);
		return smaIndicator.getValue(index).dividedBy(chop);
	}

}
