package com.stox.charting.axis;

import org.ta4j.core.num.Num;

import javafx.scene.layout.Pane;
import lombok.Setter;

public class YAxis extends Pane {

	@Setter private Num highestValue, lowestValue, axisHeight;
	
	public YAxis() {
		setWidth(10);
		setMaxWidth(10);
		setMinWidth(10);
		setPrefWidth(10);
	}
	
	public double value(Num value) {
		return axisHeight.minus(value.minus(lowestValue).multipliedBy(axisHeight)
				.dividedBy(highestValue.minus(lowestValue)))
				.doubleValue();
	}

}
