package com.stox.charting.unit;

import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;

import javafx.scene.Node;

public interface Unit<T> {
	
	public Node asNode();
	
	public void setVisible(boolean value);

	public void layoutChildren(int index, T model, XAxis xAxis, YAxis yAxis);
	
}
