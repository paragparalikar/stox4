package com.stox.indicator;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.Num;

public class CandleBodyIndicator extends AbstractIndicator<Num> {

	private final boolean abs;
	
	public CandleBodyIndicator(BarSeries series) {
		this(series, true);
	}
	
	public CandleBodyIndicator(BarSeries series, boolean abs) {
		super(series);
		this.abs = abs;
	}

	@Override
	public Num getValue(int index) {
		final BarSeries series = getBarSeries();
		if(index < series.getRemovedBarsCount() || index >= series.getBarCount()) {
			return null;
		}
		final Bar bar = series.getBar(index);
		final Num body = bar.getClosePrice().minus(bar.getOpenPrice());
		return abs ? body.abs() : body;
	}

}
