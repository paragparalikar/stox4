package com.stox.module.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum BarSpan implements Serializable, Comparable<BarSpan> {

	M(Calendar.MONTH, 1, "M", "Monthly"), W(Calendar.WEEK_OF_YEAR, 1, "W", "Weekly"), D(Calendar.DATE, 1, "D", "Daily");

	private final int unit;
	private final int count;
	private final String shortName;
	@Getter(AccessLevel.NONE)
	private final String name;

	public static BarSpan byShortName(final String shortName) {
		return Arrays.stream(values()).filter(barSpan -> Objects.equals(barSpan.shortName(), shortName)).findFirst().orElse(null);
	}

	public long millis() {
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

	@Override
	public String toString() {
		return name;
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
			calendar.setTimeInMillis(bar.date());
			final int currentIndex = calendar.get(unit()) / count();
			if (previousIndex != currentIndex) {
				higherBar = new Bar();
				higherBar.date(bar.date());
				higherBar.high(Double.MIN_VALUE);
				higherBar.low(Double.MAX_VALUE);
				higherBar.close(bar.close());
				higherBars.add(higherBar);
				previousIndex = currentIndex;
			}

			if (bar.high() > higherBar.high()) {
				higherBar.high(bar.high());
			}
			if (bar.low() < higherBar.low()) {
				higherBar.low(bar.low());
			}
			higherBar.open(bar.open());
			higherBar.volume(bar.volume() + higherBar.volume());
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
