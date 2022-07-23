package com.stox.indicator;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

public class PriceRejectionIndicator extends CachedIndicator<Num> {

	public PriceRejectionIndicator(BarSeries series) {
		super(series);
	}

	@Override
	protected Num calculate(int index) {
		final BarSeries series = getBarSeries();
		if(3 > series.getBarCount()) return null;
		final Bar bar = series.getBar(index);
		final Bar one = series.getBar(index - 1);
		final Bar two = series.getBar(index - 2);
		final Num min = bar.getLowPrice().min(one.getLowPrice()).min(two.getLowPrice());
		final Num delta = two.getOpenPrice().minus(min).plus(bar.getClosePrice().min(min));
		return delta.multipliedBy(numOf(100)).dividedBy(bar.getClosePrice());
	}

}
