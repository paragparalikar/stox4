package com.stox.module.charting.axis.vertical;

public interface YAxis {

	double getValue(double y);
	
	double getY(double value);
	
	double getMinY();

	double getHeight();
	
	double getMin();
	
	double getMax();
	
	double getTop();
	
	double getBottom();

}
