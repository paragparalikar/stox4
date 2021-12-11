package com.stox.module.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
public class Move {

	public static List<Move> parse(final List<Bar> bars, final double percentage){
		boolean up = true;
		final List<Move> moves = new ArrayList<>();
		int lastPivotIndex = bars.size() - 1, pivotIndex = lastPivotIndex;
		double pivotValue = bars.get(lastPivotIndex).low();
		double max = pivotValue * (1 + (percentage / 100));
		double min = pivotValue * (1 - (percentage / 100));
		for (int index = lastPivotIndex; index >= 0; index--) {
			final Bar bar = bars.get(index);
			if(up) {
				if(bar.low() < min) {
					up = false;
					moves.add(new Move(BarValue.LOW,
							Collections.unmodifiableList(new ArrayList<>(bars.subList(pivotIndex, lastPivotIndex + 1))),
							pivotIndex, lastPivotIndex));
					lastPivotIndex = pivotIndex;
					pivotValue = bar.low();
					pivotIndex = index;
					max = pivotValue * (1 + (percentage / 100));
				} else if (bar.high() > pivotValue) {
					pivotValue = bar.high();
					pivotIndex = index;
					min = pivotValue * (1 - (percentage / 100));
				}
			} else {
				if(bar.high() > max) {
					up = true;
					moves.add(new Move(BarValue.HIGH,
							Collections.unmodifiableList(new ArrayList<>(bars.subList(pivotIndex, lastPivotIndex + 1))),
							pivotIndex, lastPivotIndex));
					lastPivotIndex = pivotIndex;
					pivotValue = bar.high();
					pivotIndex = index;
					min = pivotValue * (1 - (percentage / 100));
				} else if (bar.low() < pivotValue) {
					pivotValue = bar.low();
					pivotIndex = index;
					max = pivotValue * (1 + (percentage / 100));
				}
			}
		}
		if(0 != pivotIndex) {
			moves.add(new Move(BarValue.LOW, Collections.unmodifiableList(new ArrayList<>(bars.subList(0, pivotIndex))),
					0, pivotIndex));
		}
		return moves;
	}
	
	public static List<Move> parse(final List<Bar> bars, final double percentage, final BarValue barValue) {
		boolean up = true;
		final List<Move> moves = new ArrayList<>();
		int lastPivotIndex = bars.size() - 1, pivotIndex = lastPivotIndex;
		double pivotValue = barValue.resolve(bars.get(lastPivotIndex));
		double max = pivotValue * (1 + (percentage / 100));
		double min = pivotValue * (1 - (percentage / 100));
		for (int index = lastPivotIndex; index >= 0; index--) {
			final double value = barValue.resolve(bars.get(index));
			if (up) {
				if (value < min) {
					up = false;
					moves.add(new Move(barValue,
							Collections.unmodifiableList(new ArrayList<>(bars.subList(pivotIndex, lastPivotIndex + 1))),
							pivotIndex, lastPivotIndex));
					lastPivotIndex = pivotIndex;
					pivotValue = value;
					pivotIndex = index;
					max = pivotValue * (1 + (percentage / 100));
				} else if (value > pivotValue) {
					pivotValue = value;
					pivotIndex = index;
					min = pivotValue * (1 - (percentage / 100));
				}
			} else {
				if (value > max) {
					up = true;
					moves.add(new Move(barValue,
							Collections.unmodifiableList(new ArrayList<>(bars.subList(pivotIndex, lastPivotIndex + 1))),
							pivotIndex, lastPivotIndex));
					lastPivotIndex = pivotIndex;
					pivotValue = value;
					pivotIndex = index;
					min = pivotValue * (1 - (percentage / 100));
				} else if (value < pivotValue) {
					pivotValue = value;
					pivotIndex = index;
					max = pivotValue * (1 + (percentage / 100));
				}
			}
		}
		if (0 != pivotIndex) {
			moves.add(new Move(barValue, Collections.unmodifiableList(new ArrayList<>(bars.subList(0, pivotIndex))),
					0, pivotIndex));
		}
		return moves;
	}

	@Getter
	@NonNull
	private final BarValue barValue;

	@NonNull
	private final List<Bar> bars;

	@Getter
	private final int startIndex, endIndex;

	public Bar end() {
		return bars.isEmpty() ? null : bars.get(bars.size() - 1);
	}

	public Bar start() {
		return bars.isEmpty() ? null : bars.get(0);
	}

	public int count() {
		return bars.size();
	}

}
