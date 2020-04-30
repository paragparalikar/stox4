package com.stox.module.core.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BarValue {

	OPEN("Open"), HIGH("High"), LOW("Low"), CLOSE("Close"), VOLUME("Volume"), SPREAD("Spread"), MID("Mid"), BODY(
			"Body"), LWICK("Lower Wick"), UWICK("Upper Wick");

	public static BarValue findByName(@NonNull final String name) {
		for (final BarValue barValue : values()) {
			if (name.equalsIgnoreCase(barValue.name)) {
				return barValue;
			}
		}
		return null;
	}

	private final String name;

	public Double resolve(final Bar bar) {
		if (null == bar) {
			return null;
		}
		switch (this) {
		case CLOSE:
			return bar.close();
		case HIGH:
			return bar.high();
		case LOW:
			return bar.low();
		case OPEN:
			return bar.open();
		case SPREAD:
			return bar.high() - bar.low();
		case MID:
			return (bar.high() + bar.low()) / 2;
		case BODY:
			return Math.abs(bar.close() - bar.open());
		case LWICK:
			return Math.min(bar.close(), bar.open()) - bar.low();
		case UWICK:
			return bar.high() - Math.max(bar.open(), bar.close());
		case VOLUME:
			return bar.volume();
		default:
			return bar.close();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}

}
