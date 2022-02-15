package com.stox.charting.unit;

import org.ta4j.core.num.Num;

import com.stox.charting.axis.XAxis;

import javafx.scene.Node;

public interface Unit<T> {
	
	public Node asNode();
	
	public void setVisible(boolean value);

	public void layoutChildren(int index, T model, XAxis xAxis,
			Num highestValue, Num lowestValue, double parentHeight);
	
}
