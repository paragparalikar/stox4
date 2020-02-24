package com.stox.module.charting.unit;

public enum PriceUnitType{

	AREA("Area"), LINE("Line"), HLC("HLC"), OHLC("OHLC"), CANDLE("Candle");

	private final String name;

	private PriceUnitType(final String name) {
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
