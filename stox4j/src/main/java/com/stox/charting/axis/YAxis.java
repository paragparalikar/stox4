package com.stox.charting.axis;

import com.stox.charting.ChartingView.ChartingConfig;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.grid.Crosshair;
import com.stox.charting.grid.HorizontalGrid;
import com.stox.common.util.Strings;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;

public class YAxis extends StackPane {
	private static final double[] TICK_UNIT_DEFAULTS = { 1.0E-10d, 2.5E-10d, 5.0E-10d, 1.0E-9d, 2.5E-9d, 5.0E-9d,
			1.0E-8d, 2.5E-8d, 5.0E-8d, 1.0E-7d, 2.5E-7d, 5.0E-7d, 1.0E-6d, 2.5E-6d, 5.0E-6d, 1.0E-5d, 2.5E-5d, 5.0E-5d,
			1.0E-4d, 2.5E-4d, 5.0E-4d, 0.0010d, 0.0025d, 0.0050d, 0.01d, 0.025d, 0.05d, 0.1d, 0.25d, 0.5d, 1.0d, 2.5d,
			5.0d, 10.0d, 25.0d, 50.0d, 100.0d, 250.0d, 500.0d, 1000.0d, 2500.0d, 5000.0d, 10000.0d, 25000.0d, 50000.0d,
			100000.0d, 250000.0d, 500000.0d, 1000000.0d, 2500000.0d, 5000000.0d, 1.0E7d, 2.5E7d, 5.0E7d, 1.0E8d, 2.5E8d,
			5.0E8d, 1.0E9d, 2.5E9d, 5.0E9d, 1.0E10d, 2.5E10d, 5.0E10d, 1.0E11d, 2.5E11d, 5.0E11d, 1.0E12d, 2.5E12d,
			5.0E12d };
	
	private final Crosshair crosshair;
	private final ChartingConfig config;
	private final ChartingContext context;
	private final Label label = new Label();
	private final Pane container = new Pane();
	private final HorizontalGrid horizontalGrid;
	@Setter @Getter private double 
			highestValue = Double.MIN_VALUE, 
			lowestValue = Double.MAX_VALUE, 
			tickHeight = 20, topMargin = 10, bottomMargin = 8;
	
	public YAxis(ChartingContext context, ChartingConfig config, 
			Crosshair crosshair, HorizontalGrid horizontalGrid) {
		style();
		this.config = config;
		this.context = context;
		this.crosshair = crosshair;
		this.horizontalGrid = horizontalGrid;
		getChildren().addAll(container, new Pane(label));
		crosshair.getHorizontalLine().endYProperty().addListener(this::onCrosshairYChanged);
	}
	
	private void onCrosshairYChanged(ObservableValue<? extends Number> observable, Number old, Number value) {
		if(null != value) {
			label.setText(Strings.toString(getValue(value.doubleValue())));
			label.setLayoutY(value.doubleValue() - label.getHeight()/2);
		}
	}
	
	private void style() {
		getStyleClass().add("y-axis");
		container.getStyleClass().add("container");
		label.getStyleClass().add("axis-info-label");
	}
	
	public void reset() {
		highestValue = Double.MIN_VALUE;
		lowestValue = Double.MAX_VALUE;
		horizontalGrid.reset();
	}
	
	public double getY(double value) {
		final double axisHeight = getHeight() - topMargin - bottomMargin;
		final double result = ((value - lowestValue) * axisHeight) /
				(highestValue - lowestValue);
		return axisHeight + topMargin - result;
	}
	
	public double getValue(double y) {
		final double axisHeight = getHeight() - topMargin - bottomMargin;
		return lowestValue + (axisHeight + topMargin - y) * (highestValue - lowestValue)/axisHeight;
	}

	public void layoutChartChildren() {
		horizontalGrid.reset();
		container.getChildren().clear();
		final double axisHeight = getHeight() - topMargin - bottomMargin;
		final double tickSize = snap((highestValue - lowestValue) * tickHeight / axisHeight);
		
		final double labelHeight = (axisHeight * tickSize )/(highestValue - lowestValue);
		final double highest = tickSize * Math.floor(highestValue / tickSize);
		for(double value = highest; value > lowestValue; value -= tickSize) {
			addLabel(value, labelHeight);
		}
	}
	
	private void addLabel(double value, double labelHeight) {
		final Label label = new Label(Strings.toString(value));
		final double y = getY(value);
		horizontalGrid.addLine(y);
		container.getChildren().add(label);
		label.layoutYProperty().bind(label.heightProperty().divide(2).subtract(y).negate());
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
