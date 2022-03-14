package com.stox.charting.grid;

import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

public class HorizontalGrid extends AbstractGrid {

	@Override
	protected Line addLine(double value, Region parent) {
		final Line line = addLine(0, value, parent.getWidth(), value);
		line.endYProperty().bind(line.startYProperty());
		return line;
	}

}
