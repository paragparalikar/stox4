package com.stox.charting.unit;

import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;

public interface Unit<T, C> {
	
	public C asChild();
	
	public void setXAxis(XAxis xAxis);
	public void setYAxis(YAxis yAxis);
	public void setContext(ChartingContext context);

	public void layoutChildren(int index, T model);
	
}
