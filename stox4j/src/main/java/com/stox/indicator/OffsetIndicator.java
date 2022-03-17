package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class OffsetIndicator<T> implements Indicator<T> {

	private final int offset;
	private final Indicator<T> delegate;
	
	@Override
	public T getValue(int index) {
		final int i = index + offset;
		return i >= 0 && i < getBarSeries().getBarCount() ? delegate.getValue(index + offset) : null;
	}

	@Override
	public BarSeries getBarSeries() {
		return delegate.getBarSeries();
	}

	@Override
	public Num numOf(Number number) {
		return delegate.numOf(number);
	}

}
