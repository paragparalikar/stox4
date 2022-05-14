package com.stox.common.bar;

import java.util.function.Function;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.OpenPriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.Num;

public enum BarValueType {

	OPEN(OpenPriceIndicator::new),
	HIGH(HighPriceIndicator::new),
	LOW(LowPriceIndicator::new),
	CLOSE(ClosePriceIndicator::new),
	VOLUME(VolumeIndicator::new);
	
	private final Function<BarSeries, Indicator<Num>> priceIndicatorFunction;
	
	private BarValueType(Function<BarSeries, Indicator<Num>> priceIndicatorFunction) {
		this.priceIndicatorFunction = priceIndicatorFunction;
	}
	
	public Indicator<Num> createIndicator(BarSeries series) {
		return priceIndicatorFunction.apply(series);
	}
	
}
