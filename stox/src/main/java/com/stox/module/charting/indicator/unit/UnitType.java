package com.stox.module.charting.indicator.unit;

public enum UnitType  {

	BAR("Bar"), LINE("Line"), AREA("Area");

	private final String name;

	private UnitType(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
