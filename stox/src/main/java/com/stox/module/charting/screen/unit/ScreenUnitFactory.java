package com.stox.module.charting.screen.unit;

import com.stox.module.screen.ScreenType;

import javafx.scene.Group;

public class ScreenUnitFactory {

	private interface Wrapper {
		ScreenUnitFactory INSTANCE = new ScreenUnitFactory();
	}

	public static ScreenUnitFactory getInstance() {
		return Wrapper.INSTANCE;
	}

	private ScreenUnitFactory() {

	}

	public AbstractScreenUnit build(final ScreenType screenType, final Group parent) {
		switch (screenType) {
		case BEARISH:
			return new BearishScreenUnit(parent);
		case BULLISH:
			return new BullishScreenUnit(parent);
		case NEUTRAL:
			return new NeutralScreenUnit(parent);
		default:
			throw new IllegalArgumentException("ScreenType \"" + screenType + "\" is not supported");
		}
	}

}
