package com.stox.charting.unit;

import org.ta4j.core.num.Num;

import javafx.scene.Node;

public interface Unit<T> {
	
	public Node asNode();
	
	public void setVisible(boolean value);

	public void layoutChildren(T model, 
			Num highestValue, Num lowestValue, double parentHeight,
			int barIndex, int visibleBarCount, double parentWidth);
	
}
