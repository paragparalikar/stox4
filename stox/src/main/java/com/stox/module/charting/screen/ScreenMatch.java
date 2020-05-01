package com.stox.module.charting.screen;

import java.util.Comparator;
import java.util.Objects;

import com.stox.module.core.model.Bar;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class ScreenMatch implements Comparable<ScreenMatch> {
	private static final Comparator<ScreenMatch> COMPARATOR = (one, two) -> one.index().compareTo(two.index());

	@NonNull
	private Bar bar;

	@NonNull
	private Integer index;

	@Override
	public int compareTo(ScreenMatch other) {
		return Objects.compare(this, other, COMPARATOR);
	}

}
