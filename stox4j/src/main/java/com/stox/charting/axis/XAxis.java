package com.stox.charting.axis;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class XAxis extends StackPane {
	public static final double HEIGHT = 16;

	private final HBox container = new HBox(); 
	private double unitWidth = 10, maxUnitWidth = 50, minUnitWidth = 1, panWidth;
	
	public XAxis() {
		setMaxHeight(HEIGHT);
		setPrefHeight(HEIGHT);
		setMinHeight(HEIGHT);
		setHeight(HEIGHT);
		
		HBox.setHgrow(container, Priority.ALWAYS);
		final Rectangle rectangle = new Rectangle(YAxis.WIDTH, XAxis.HEIGHT);
		rectangle.setFill(Color.TRANSPARENT);
		getChildren().add(new HBox(container, rectangle));
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
		final double margin = YAxis.WIDTH + 2 * unitWidth;
		return (int) ((panWidth + margin) / unitWidth);
	}
	
	public void pan(final double deltaX) {
		panWidth += deltaX;
	}
	
	public void resetPanWidth() {
		panWidth = -100;
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
