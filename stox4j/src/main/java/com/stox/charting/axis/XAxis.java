package com.stox.charting.axis;

import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class XAxis extends Pane {

	private double unitWidth = 10, maxUnitWidth = 50, minUnitWidth = 1, panWidth;
	
	public XAxis() {
		setMaxHeight(10);
		setPrefHeight(10);
		setMinHeight(10);
		setHeight(10);
	}
	
	public double getX(final int index) {
		return getWidth() + panWidth - index * unitWidth;
	}

	public int getIndex(final double x) {
		return (int)Math.ceil((getWidth() + panWidth - x) / unitWidth);
	}

	public int getEndIndex() {
		return (int) ((panWidth + getWidth()) / unitWidth);
	}
	
	public int getStartIndex() {
		return (int) (panWidth / unitWidth);
	}
	
	public void pan(final double deltaX) {
		panWidth += deltaX;
	}
	
	public void zoom(final double x, final int percentage) {
		double newUnitWidth = unitWidth * (100 + percentage) / 100;
		if (newUnitWidth >= minUnitWidth && newUnitWidth <= maxUnitWidth && newUnitWidth != unitWidth) {
			final double position = (x - panWidth) / unitWidth;
			unitWidth = newUnitWidth;
			panWidth = x - position * unitWidth;
		}
	}
}
