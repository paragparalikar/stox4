package com.stox.indicator;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.Num;

public class PriceSpreadIndicator extends AbstractIndicator<Num> {

	public PriceSpreadIndicator(BarSeries series) {
		super(series);
	}

	@Override
	public Num getValue(int index) {
		final BarSeries series = getBarSeries();
		if(index < series.getRemovedBarsCount() || index >= series.getBarCount()) {
			return null;
		}
		final Bar bar = series.getBar(index);
		return bar.getHighPrice().minus(bar.getLowPrice());
	}

}
