package com.stox.charting.axis.y;

import com.stox.common.util.Strings;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class YAxisValueLabelDecorator {
	private static final double[] TICK_UNIT_DEFAULTS = { 1.0E-10d, 2.5E-10d, 5.0E-10d, 1.0E-9d, 2.5E-9d, 5.0E-9d,
			1.0E-8d, 2.5E-8d, 5.0E-8d, 1.0E-7d, 2.5E-7d, 5.0E-7d, 1.0E-6d, 2.5E-6d, 5.0E-6d, 1.0E-5d, 2.5E-5d, 5.0E-5d,
			1.0E-4d, 2.5E-4d, 5.0E-4d, 0.0010d, 0.0025d, 0.0050d, 0.01d, 0.025d, 0.05d, 0.1d, 0.25d, 0.5d, 1.0d, 2.5d,
			5.0d, 10.0d, 25.0d, 50.0d, 100.0d, 250.0d, 500.0d, 1000.0d, 2500.0d, 5000.0d, 10000.0d, 25000.0d, 50000.0d,
			100000.0d, 250000.0d, 500000.0d, 1000000.0d, 2500000.0d, 5000000.0d, 1.0E7d, 2.5E7d, 5.0E7d, 1.0E8d, 2.5E8d,
			5.0E8d, 1.0E9d, 2.5E9d, 5.0E9d, 1.0E10d, 2.5E10d, 5.0E10d, 1.0E11d, 2.5E11d, 5.0E11d, 1.0E12d, 2.5E12d,
			5.0E12d };

	public YAxis decorate(YAxis yAxis, Pane container) {
		yAxis.getChildren().addAll(container);
		container.getStyleClass().add("container");
		yAxis.addEventHandler(YAxisRedrawRequestEvent.TYPE, event -> layoutChartChildren(yAxis, container));
		return yAxis;
	}
	
	public void layoutChartChildren(YAxis yAxis, Pane container) {
		container.getChildren().clear();
		final double axisHeight = yAxis.getHeight() - yAxis.getTopMargin() - yAxis.getBottomMargin();
		final double tickSize = snap((yAxis.getHighestValue() - yAxis.getLowestValue()) * yAxis.getTickHeight() / axisHeight);
		
		final double labelHeight = (axisHeight * tickSize )/(yAxis.getHighestValue() - yAxis.getLowestValue());
		final double highest = tickSize * Math.floor(yAxis.getHighestValue() / tickSize);
		for(double value = highest; value > yAxis.getLowestValue(); value -= tickSize) {
			addLabel(yAxis, container, value, labelHeight);
		}
	}
	
	protected double addLabel(YAxis yAxis, Pane container, double value, double labelHeight) {
		final Label label = new Label(Strings.toString(value));
		final double y = yAxis.getY(value);
		container.getChildren().add(label);
		label.layoutYProperty().bind(label.heightProperty().divide(2).subtract(y).negate());
		return y;
	}
	
	private double snap(double value) {
		for (final double tickUnitDefault : TICK_UNIT_DEFAULTS) {
			if (value <= tickUnitDefault) {
				return tickUnitDefault;
			}
		}
		return value;
	}
	
}
