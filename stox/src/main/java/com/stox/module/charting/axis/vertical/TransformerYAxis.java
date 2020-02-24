package com.stox.module.charting.axis.vertical;

import lombok.NonNull;
import lombok.Setter;

@Setter
public class TransformerYAxis implements DelegatingYAxis {

	@NonNull
	private YAxis delegate;
	private double min, max;

	@Override
	public double getValue(double y) {
		final double top = delegate.getTop();
		final double bottom = delegate.getBottom();
		final double height = delegate.getHeight();
		return min + (((height - top) - y) * (max - min)) / (height - top - bottom);
	}

	@Override
	public double getY(double value) {
		final double top = delegate.getTop();
		final double bottom = delegate.getBottom();
		final double height = delegate.getHeight();
		return (height - top) - ((value - min) / (max - min)) * (height - top - bottom);
	}

	@Override
	public double getMinY() {
		return delegate.getMinY();
	}

	@Override
	public double getHeight() {
		return delegate.getHeight();
	}

	@Override
	public double getMin() {
		return min;
	}

	@Override
	public double getMax() {
		return max;
	}

	@Override
	public double getTop() {
		return delegate.getTop();
	}

	@Override
	public double getBottom() {
		return delegate.getBottom();
	}

}
