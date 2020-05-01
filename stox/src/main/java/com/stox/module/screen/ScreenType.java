package com.stox.module.screen;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScreenType {

	BULLISH("Bullish"), BEARISH("Bearish"), NEUTRAL("Neutral");

	private final String name;

	@Override
	public String toString() {
		return name;
	}
}
