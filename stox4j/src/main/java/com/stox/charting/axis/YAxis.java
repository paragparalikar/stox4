package com.stox.charting.axis;

import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

import javafx.scene.layout.Pane;
import lombok.Setter;

public class YAxis extends Pane {
	public static final double WIDTH = 30;
	
	private Num axisHeight;
	@Setter private Num highestValue, lowestValue, 
		topMargin = DoubleNum.valueOf(20), 
		bottomMargin = DoubleNum.valueOf(20);
	
	public YAxis() {
		setWidth(WIDTH);
		setMaxWidth(WIDTH);
		setMinWidth(WIDTH);
		setPrefWidth(WIDTH);
		heightProperty().addListener((o,old,value)-> {
			final Num height = DoubleNum.valueOf(value);
			axisHeight = height.minus(topMargin).minus(bottomMargin);
		});
	}
	
	public double value(Num value) {
		final Num result = value.minus(lowestValue).multipliedBy(axisHeight)
				.dividedBy(highestValue.minus(lowestValue));
		return axisHeight.plus(topMargin).minus(result).doubleValue();
	}

}
