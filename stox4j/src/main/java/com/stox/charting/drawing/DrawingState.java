package com.stox.charting.drawing;

import java.io.Serializable;

import com.stox.charting.chart.Chart;

public interface DrawingState extends Serializable {

	Drawing<?> create(Chart chart);
	
}
