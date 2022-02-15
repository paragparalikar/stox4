package com.stox.charting.grid;

import javafx.scene.layout.Region;

public class VerticalGrid extends AbstractGrid {

	@Override
	protected void addLine(double value, Region parent) {
		addLine(value, 0, value, parent.getHeight());
	}

}
