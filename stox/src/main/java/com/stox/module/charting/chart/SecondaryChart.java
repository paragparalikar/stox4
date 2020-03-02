package com.stox.module.charting.chart;

import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.DelegatingYAxis;
import com.stox.module.charting.axis.vertical.ValueAxis;

public class SecondaryChart extends Chart<SecondaryChartState> {

	public SecondaryChart(Configuration configuration, XAxis xAxis, final DelegatingYAxis volumeYAxis) {
		super(xAxis, configuration, volumeYAxis);
		valueAxis(new ValueAxis(5));
		yAxis().setTop(0);
		yAxis().setBottom(0);
		bind();
	}

	@Override
	public SecondaryChartState state() {
		return fill(new SecondaryChartState());
	}

}
