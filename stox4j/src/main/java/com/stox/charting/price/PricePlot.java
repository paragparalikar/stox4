package com.stox.charting.price;

import org.ta4j.core.Bar;
import org.ta4j.core.Indicator;

import com.stox.charting.Plot;
import com.stox.charting.unit.CandleUnit;
import com.stox.charting.unit.resolver.BarHighLowResolver;

public class PricePlot extends Plot<Bar> {

	public PricePlot(Indicator<Bar> indicator) {
		super(indicator, CandleUnit::new, new BarHighLowResolver());
	}

}
