package com.stox.charting.drawing;

import java.io.Serializable;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.y.YAxis;

public interface DrawingState extends Serializable {

	Drawing<?> create(XAxis xAxis, YAxis yAxis, ChartingContext context);
	
}
