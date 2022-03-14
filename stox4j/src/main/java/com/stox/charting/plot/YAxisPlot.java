package com.stox.charting.plot;

import com.stox.charting.axis.y.YAxis;

public class YAxisPlot<T, C, N> extends Plot<T, C, N> {

	//private final YAxis yAxis = new YAxis();
	
	public YAxisPlot(Plottable<T, C, N> plottable) {
		super(plottable);
	}
	
	protected void updateYAxis(int startIndex, int endIndex) {
		
	}

}
