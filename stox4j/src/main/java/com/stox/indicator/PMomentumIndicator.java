package com.stox.indicator;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

public class PMomentumIndicator extends CachedIndicator<Num> {

	private final int barCount;
	
	public PMomentumIndicator(int barCount, BarSeries series) {
		super(series);
		this.barCount = barCount;
	}

	@Override
	protected Num calculate(int index) {
		final BarSeries series = getBarSeries();
		int changeCounter = 0;
		double changeSum = 0;
		int closeCounter = 0;
		double closeSum = 0;
		
		for(int leftIndex = Math.max(index - barCount + 1, 0); leftIndex < index; leftIndex++) {
			final Bar left = series.getBar(leftIndex);
			closeSum += left.getClosePrice().doubleValue();
			closeCounter++;
			for(int rightIndex = leftIndex + 1; rightIndex <= index; rightIndex++) {
				final Bar right = series.getBar(rightIndex);
				changeSum += left.getClosePrice().minus(right.getClosePrice()).abs().doubleValue();
				changeCounter++;
			}
		}
		
		final double averageClose = closeSum / closeCounter;
		final double averageChange = changeSum / changeCounter;
		return series.numOf(averageChange * 100 / averageClose);
	}

}
