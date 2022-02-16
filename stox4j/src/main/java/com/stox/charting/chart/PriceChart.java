package com.stox.charting.chart;

import com.stox.charting.ChartingContext;
import com.stox.charting.plot.PricePlot;

public class PriceChart extends Chart {

	public PriceChart(ChartingContext context, PricePlot pricePlot) {
		super(context);
		add(pricePlot);
	}
	
}
