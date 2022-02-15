package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.common.bar.Bar;

import lombok.NonNull;
import lombok.Setter;

public class BarIndicator implements Indicator<Bar> {

	@Setter @NonNull private BarSeries barSeries;

	@Override
	public Bar getValue(int index) {
		return (Bar) barSeries.getBar(index);
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
