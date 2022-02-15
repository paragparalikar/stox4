package com.stox.charting.unit.resolver;

import org.ta4j.core.num.Num;

public class NumHighLowResolver implements HighLowResolver<Num> {

	@Override
	public Num resolveLow(Num model) {
		return model;
	}

	@Override
	public Num resolveHigh(Num model) {
		return model;
	}

	

}
