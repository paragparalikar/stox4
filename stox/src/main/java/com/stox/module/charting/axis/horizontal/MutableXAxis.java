package com.stox.module.charting.axis.horizontal;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.util.MathUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MutableXAxis implements XAxis {

	private List<Bar> bars;
	private double unitWidth = 10, maxUnitWidth = 50, minUnitWidth = 1, pivotX, width;
	
	public MutableXAxisState state() {
		return new MutableXAxisState()
				.unitWidth(maxUnitWidth)
				.maxUnitWidth(maxUnitWidth)
				.minUnitWidth(minUnitWidth)
				.pivotX(pivotX)
				.width(width);
	}

	public double getX(final int index) {
		return pivotX - index * unitWidth;
	}

	public int getIndex(final double x) {
		return (int) Math.ceil(((pivotX - x) / unitWidth));
	}

	public int getStartIndex() {
		return (int) ((pivotX - width) / unitWidth);
	}

	@Override
	public int getClippedStartIndex() {
		return MathUtil.clip(0, getStartIndex(), Math.max(0, getCount() - 1));
	}

	public int getEndIndex() {
		return (int) (pivotX / unitWidth);
	}

	@Override
	public int getClippedEndIndex() {
		return MathUtil.clip(0, getEndIndex(), Math.max(0, getCount() - 1));
	}

	public void pan(final double deltaX) {
		pivotX += deltaX;
	}

	@Override
	public int getCount() {
		return null == bars ? 0 : bars.size();
	}

	public void zoom(final double x, final int percentage) {
		double newUnitWidth = unitWidth * (100 + percentage) / 100;
		if (newUnitWidth >= minUnitWidth && newUnitWidth <= maxUnitWidth && newUnitWidth != unitWidth) {
			final double position = (x - pivotX) / unitWidth;
			unitWidth = newUnitWidth;
			pivotX = x - position * unitWidth;
		}
	}

	@Override
	public long getDate(double x) {
		return getDate(getIndex(x));
	}

	@Override
	public long getDate(int index) {
		return null == bars || bars.isEmpty() ? 0
				: (0 <= index && index < bars.size()) ? bars.get(index).getDate()
						: (long) MathUtil.praportion(0, index, bars.size() - 1, bars.get(0).getDate(),
								bars.get(bars.size() - 1).getDate());
	}

	@Override
	public double getX(long date) {
		return getX(getIndex(date));
	}

	@Override
	public int getIndex(long date) {
		return null == bars || bars.isEmpty() ? -1 : getIndex(date, bars.size() - 1, 0);
	}

	// Interpolation binary search
	private int getIndex(long date, int startIndex, int endIndex) {
		final Bar start = bars.get(startIndex);
		final Bar end = bars.get(endIndex);
		if (date == start.getDate()) {
			return startIndex;
		} else if (date == end.getDate()) {
			return endIndex;
		} else if (date < start.getDate() || date > end.getDate()) {
			return (int) MathUtil.praportion(start.getDate(), date, end.getDate(), startIndex, endIndex);
		} else {
			final int index = (int) MathUtil.praportion(start.getDate(), date, end.getDate(), startIndex, endIndex);
			if(index == startIndex || index == endIndex){
				return index;
			}
			final long interpolatedIndexDate = bars.get(index).getDate();
			if (interpolatedIndexDate == date) {
				return index;
			} else if (interpolatedIndexDate < date) {
				return getIndex(date, index, endIndex);
			} else {
				return getIndex(date, startIndex, index);
			}
		}
	}
	
}
