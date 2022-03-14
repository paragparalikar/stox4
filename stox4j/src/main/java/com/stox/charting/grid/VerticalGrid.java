package com.stox.charting.grid;

import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

public class VerticalGrid extends AbstractGrid {

	@Override
	protected Line addLine(double value, Region parent) {
		return addLine(value, 0, value, parent.getHeight());
	}

}
