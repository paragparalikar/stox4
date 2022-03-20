package com.stox.indicator;

import java.util.Arrays;
import java.util.Objects;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.Num;

public class PlusIndicator extends AbstractIndicator<Num> {

	private final Indicator<Num>[] indicators;
	
	@SafeVarargs
	protected PlusIndicator(BarSeries series, Indicator<Num>...indicators) {
		super(series);
		this.indicators = indicators;
	}

	@Override
	public Num getValue(int index) {
		return Arrays.stream(indicators)
				.filter(Objects::nonNull)
				.map(indicator -> indicator.getValue(index))
				.reduce((one, two) -> one.plus(two))
				.orElse(null);
	}

}
