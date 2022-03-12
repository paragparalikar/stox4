package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;

public class ZigZagIndicator extends CachedIndicator<Move> {

	protected ZigZagIndicator(BarSeries series) {
		super(series);
	}

	@Override
	protected Move calculate(int index) {
		
		return null;
	}
	
}
