package com.stox.charting.unit.resolver;

import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

public class NumberHighLowResolver<T extends Number> implements HighLowResolver<T> {
	
	@Override
	public Num resolveLow(Number model) {
		return DoubleNum.valueOf(model);
	}

	@Override
	public Num resolveHigh(Number model) {
		return DoubleNum.valueOf(model);
	}

}
