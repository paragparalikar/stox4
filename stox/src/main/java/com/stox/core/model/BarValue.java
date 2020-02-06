package com.stox.core.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BarValue {

	OPEN("Open"), HIGH("High"), LOW("Low"), CLOSE("Close"), VOLUME("Volume"), SPREAD("Spread"), MID("Mid"), BODY(
			"Body"), LWICK("Lower Wick"), UWICK("Upper Wick");

	public static BarValue findByName(@NonNull final String name) {
		for (final BarValue barValue : values()) {
			if (name.equalsIgnoreCase(barValue.getName())) {
				return barValue;
			}
		}
		return null;
	}

	private final String name;

	public Double get(final Bar bar) {
		if (null == bar) {
			return null;
		}
		switch (this) {
		case CLOSE:
			return bar.getClose();
		case HIGH:
			return bar.getHigh();
		case LOW:
			return bar.getLow();
		case OPEN:
			return bar.getOpen();
		case SPREAD:
			return bar.getHigh() - bar.getLow();
		case MID:
			return (bar.getHigh() + bar.getLow()) / 2;
		case BODY:
			return Math.abs(bar.getClose() - bar.getOpen());
		case LWICK:
			return Math.min(bar.getClose(), bar.getOpen()) - bar.getLow();
		case UWICK:
			return bar.getHigh() - Math.max(bar.getOpen(), bar.getClose());
		case VOLUME:
			return bar.getVolume();
		default:
			return bar.getClose();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}

}
