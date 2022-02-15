package com.stox.charting.unit.resolver;

import org.ta4j.core.num.Num;

public interface HighLowResolver<T> {

	Num resolveLow(T model);
	
	Num resolveHigh(T model);
	
}
