package com.stox.charting.plot.rule;

import com.stox.charting.plot.Plot;
import com.stox.charting.plot.Plottable;

import javafx.scene.Node;

public class RulePlot<C> extends Plot<Boolean, C, Node> {

	public RulePlot(Plottable<Boolean, C, Node> plottable) {
		super(plottable);
	}
	
	@Override public void updateYAxis(int startIndex, int endIndex) {}
}
