package com.stox.charting.unit.resolver;

import org.ta4j.core.num.Num;

import com.stox.common.bar.Bar;

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
