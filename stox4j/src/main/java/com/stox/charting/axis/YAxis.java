package com.stox.charting.axis;

import org.ta4j.core.num.Num;

import javafx.scene.layout.Pane;
import lombok.Setter;

public class YAxis extends Pane {
	public static final double WIDTH = 30;
	
	
	@Setter private Num highestValue, lowestValue, axisHeight;
	
	public YAxis() {
		setWidth(WIDTH);
		setMaxWidth(WIDTH);
		setMinWidth(WIDTH);
		setPrefWidth(WIDTH);
	}
	
	public double value(Num value) {
		return axisHeight.minus(value.minus(lowestValue).multipliedBy(axisHeight)
				.dividedBy(highestValue.minus(lowestValue)))
				.doubleValue();
	}

}
