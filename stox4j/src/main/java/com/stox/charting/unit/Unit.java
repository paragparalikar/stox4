package com.stox.charting.unit;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;

import javafx.scene.Node;

public interface Unit<T> {
	
	public Node asNode();
	
	public void setXAxis(XAxis xAxis);
	public void setYAxis(YAxis yAxis);
	public void setVisible(boolean value);
	public void setContext(ChartingContext context);

	public void layoutChildren(int index, T model);
	
}
