package com.stox.module.charting.axis.vertical;

public interface DelegatingYAxis extends YAxis {
	
	void setDelegate(YAxis yAxis);

}
