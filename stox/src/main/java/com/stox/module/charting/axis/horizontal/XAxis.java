package com.stox.module.charting.axis.horizontal;

public interface XAxis {

	int getCount();

	double getWidth();

	double getUnitWidth();

	double getX(final int index);

	int getIndex(final double x);
	
	int getStartIndex();
	
	int getClippedStartIndex();

	int getEndIndex();
	
	int getClippedEndIndex();
	
	long getDate(final double x);
	
	long getDate(final int index);

	double getX(final long date);
	
	int getIndex(final long date);

	void pan(final double deltaX);

	void zoom(final double x, final int percentage);

}
