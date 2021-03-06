package com.stox.module.charting.axis.vertical;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MutableYAxis implements YAxis {

	private boolean semilog;
	private double top = 20, bottom = 20, height, min, max;

	public void reset() {
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;
	}

	public double getValue(double y) {
		final double min = semilog ? Math.log(this.min) : this.min;
		final double max = semilog ? Math.log(this.max) : this.max;
		final double value = min + (((height - top) - y) * (max - min)) / (height - top - bottom);
		return semilog ? Math.pow(Math.E, value) : value;
	}

	public double getY(double value) {
		value = semilog ? Math.log(value) : value;
		final double min = semilog ? Math.log(this.min) : this.min;
		final double max = semilog ? Math.log(this.max) : this.max;
		return (height - top) - ((value - min) / (max - min)) * (height - top - bottom);
	}

	public double getMinY() {
		return height - top;
	}

}
