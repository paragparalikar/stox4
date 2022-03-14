package com.stox.charting.axis.y;

import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter 
public class YAxis extends StackPane {
	
	private double 
			highestValue = Double.MIN_VALUE, 
			lowestValue = Double.MAX_VALUE, 
			tickHeight = 20, topMargin = 10, bottomMargin = 8;
	
	public YAxis() {
		getStyleClass().add("y-axis");
	}
	
	public void reset() {
		highestValue = Double.MIN_VALUE;
		lowestValue = Double.MAX_VALUE;
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
	
}
