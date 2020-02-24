package com.stox.module.charting.plot;

public enum Underlay {

	PRICE("Price"), VOLUME("Volume"), NONE("None");

	private final String name;

	private Underlay(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
