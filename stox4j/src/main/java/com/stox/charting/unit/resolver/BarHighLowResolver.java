package com.stox.charting.unit.resolver;

import org.ta4j.core.Bar;
import org.ta4j.core.num.Num;

public class BarHighLowResolver implements HighLowResolver<Bar> {

	@Override
	public Num resolveLow(Bar model) {
		return null == model ? null : model.getLowPrice();
	}

	@Override
	public Num resolveHigh(Bar model) {
		return null == model ? null : model.getHighPrice();
	}

	

}
