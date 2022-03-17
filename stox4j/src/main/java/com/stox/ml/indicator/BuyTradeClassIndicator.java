package com.stox.ml.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;

public class BuyTradeClassIndicator extends CachedIndicator<Integer> {

	protected BuyTradeClassIndicator(BarSeries series) {
		super(series);
	}

	@Override
	protected Integer calculate(int index) {
		return null;
	}

}
