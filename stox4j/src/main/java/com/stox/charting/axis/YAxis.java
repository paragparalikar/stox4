package com.stox.charting.axis;

import org.ta4j.core.num.Num;

import com.stox.common.util.Strings;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;

public class YAxis extends StackPane {
	public static final double WIDTH = 64;
	private static final Font FONT = Font.font(12);
	private static final Insets PADDING = new Insets(0, 0, 0, 3);
	private static final double[] TICK_UNIT_DEFAULTS = { 1.0E-10d, 2.5E-10d, 5.0E-10d, 1.0E-9d, 2.5E-9d, 5.0E-9d,
			1.0E-8d, 2.5E-8d, 5.0E-8d, 1.0E-7d, 2.5E-7d, 5.0E-7d, 1.0E-6d, 2.5E-6d, 5.0E-6d, 1.0E-5d, 2.5E-5d, 5.0E-5d,
			1.0E-4d, 2.5E-4d, 5.0E-4d, 0.0010d, 0.0025d, 0.0050d, 0.01d, 0.025d, 0.05d, 0.1d, 0.25d, 0.5d, 1.0d, 2.5d,
			5.0d, 10.0d, 25.0d, 50.0d, 100.0d, 250.0d, 500.0d, 1000.0d, 2500.0d, 5000.0d, 10000.0d, 25000.0d, 50000.0d,
			100000.0d, 250000.0d, 500000.0d, 1000000.0d, 2500000.0d, 5000000.0d, 1.0E7d, 2.5E7d, 5.0E7d, 1.0E8d, 2.5E8d,
			5.0E8d, 1.0E9d, 2.5E9d, 5.0E9d, 1.0E10d, 2.5E10d, 5.0E10d, 1.0E11d, 2.5E11d, 5.0E11d, 1.0E12d, 2.5E12d,
			5.0E12d };
	
	private VBox container = new VBox();
	@Setter @Getter private double 
			highestValue = Double.MIN_VALUE, 
			lowestValue = Double.MAX_VALUE, 
			tickHeight = 20, topMargin = 10, bottomMargin = 8;
	
	public YAxis() {
		setWidth(WIDTH);
		setMaxWidth(WIDTH);
		setMinWidth(WIDTH);
		setPrefWidth(WIDTH);
		getChildren().add(container);
		setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
	}
	
	public void reset() {
		highestValue = Double.MIN_VALUE;
		lowestValue = Double.MAX_VALUE;
	}
	
	public double getY(Num value) {
		final double axisHeight = getHeight() - topMargin - bottomMargin;
		final double result = ((value.doubleValue() - lowestValue) * axisHeight) /
				(highestValue - lowestValue);
		return axisHeight + topMargin - result;
	}

	public void layoutChartChildren() {
		container.getChildren().clear();
		final double axisHeight = getHeight() - topMargin - bottomMargin;
		final double tickSize = snap((highestValue - lowestValue) * tickHeight / axisHeight);
		final double labelHeight = (axisHeight * tickSize )/(highestValue - lowestValue);
		final double highest = tickSize * Math.floor(highestValue / tickSize);
		for(double value = highest; value > lowestValue; value -= tickSize) {
			final Label label = new Label(Strings.stringValueOf(value));
			label.setFont(FONT);
			label.setPadding(PADDING);
			label.setPrefHeight(labelHeight);
			label.setMinHeight(labelHeight);
			label.setMaxHeight(labelHeight);
			container.getChildren().add(label);
		}
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
