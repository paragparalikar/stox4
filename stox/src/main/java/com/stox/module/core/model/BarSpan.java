package com.stox.module.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public enum BarSpan implements Serializable, Comparable<BarSpan> {

	M(Calendar.MONTH, 1, "M", "Monthly"), W(Calendar.WEEK_OF_YEAR, 1, "W", "Weekly"), D(Calendar.DATE, 1, "D", "Daily");

	private final int unit;
	private final int count;
	private final String name;
	private final String shortName;

	public static BarSpan getByShortName(final String shortName) {
		return Arrays.stream(values()).filter(barSpan -> Objects.equals(barSpan.getShortName(), shortName)).findFirst().orElse(null);
	}

	BarSpan(final int unit, final int count, final String shortName, final String name) {
		this.unit = unit;
		this.count = count;
		this.name = name;
		this.shortName = shortName;
	}

	public long getMillis() {
		switch (unit) {
			case Calendar.MINUTE:
				return 1000 * 60 * count;
			case Calendar.HOUR:
				return 1000 * 60 * 60 * count;
			case Calendar.DATE:
				return 1000 * 60 * 60 * 24 * count;
			case Calendar.WEEK_OF_YEAR:
				return 1000 * 60 * 60 * 24 * 7 * count;
			case Calendar.MONTH:
				return 1000 * 60 * 60 * 24 * 30 * count;
		}
		return 0;
	}

	public int getUnit() {
		return unit;
	}

	public int getCount() {
		return count;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * This method assumes that provided bars are at lower frequency. If not the
	 * list will be returned as it is, no exception will be thrown.
	 *
	 * @param bars lower frequency bars to be merged
	 * @return bars at the frequency of current {@link BarSpan}
	 */
	public List<Bar> merge(final List<Bar> bars) {
		if ((null == bars) || bars.isEmpty()) {
			return bars;
		}
		Collections.sort(bars);
		final Calendar calendar = Calendar.getInstance();
		final List<Bar> higherBars = new ArrayList<Bar>();
		int previousIndex = -1;
		Bar higherBar = null;

		for (final Bar bar : bars) {
			calendar.setTimeInMillis(bar.getDate());
			final int currentIndex = calendar.get(getUnit()) / getCount();
			if (previousIndex != currentIndex) {
				higherBar = new Bar();
				higherBar.setDate(bar.getDate());
				higherBar.setHigh(Double.MIN_VALUE);
				higherBar.setLow(Double.MAX_VALUE);
				higherBar.setClose(bar.getClose());
				higherBars.add(higherBar);
				previousIndex = currentIndex;
			}

			if (bar.getHigh() > higherBar.getHigh()) {
				higherBar.setHigh(bar.getHigh());
			}
			if (bar.getLow() < higherBar.getLow()) {
				higherBar.setLow(bar.getLow());
			}
			higherBar.setOpen(bar.getOpen());
			higherBar.setVolume(bar.getVolume() + higherBar.getVolume());
		}
		return higherBars;
	}

	private final Calendar calendar = Calendar.getInstance();

	public long next(final long date) {
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			calendar.add(unit, count);
			return calendar.getTimeInMillis();
		}
	}

}
