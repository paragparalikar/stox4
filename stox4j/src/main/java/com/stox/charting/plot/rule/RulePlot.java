package com.stox.charting.plot.rule;

import com.stox.charting.plot.Plot;
import com.stox.charting.plot.Plottable;

public class RulePlot extends Plot<Boolean> {

	public RulePlot(Plottable<Boolean, ?> plottable) {
		super(plottable);
	}
	
	@Override public void updateYAxis(int startIndex, int endIndex) {}
}
