package com.stox.module.charting.grid;

import javafx.scene.layout.Region;

public class HorizontalGrid extends AbstractGrid {

	@Override
	protected void addLine(double value, Region parent) {
		addLine(0, value, parent.getWidth(), value);
	}

}
