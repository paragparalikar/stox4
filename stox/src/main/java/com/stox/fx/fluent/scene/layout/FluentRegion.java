package com.stox.fx.fluent.scene.layout;

import javafx.scene.layout.Region;

public class FluentRegion extends Region implements IFluentRegion<FluentRegion> {

	public FluentRegion() {
	}

	@Override
	public FluentRegion getThis() {
		return this;
	}

}
