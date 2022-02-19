package com.stox.indicator;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BarIndicator implements Indicator<Bar> {

	@NonNull private final BarSeries barSeries;

	@Override
	public Bar getValue(int index) {
		return 0 <= index && index < barSeries.getBarCount() ? barSeries.getBar(index) : null;
	}

	@Override
	public BarSeries getBarSeries() {
		return barSeries;
	}

	@Override
	public Num numOf(Number number) {
		return barSeries.numOf(number);
	}
	
}
