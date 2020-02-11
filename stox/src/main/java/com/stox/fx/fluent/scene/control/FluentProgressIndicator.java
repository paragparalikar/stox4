package com.stox.fx.fluent.scene.control;

import javafx.scene.control.ProgressIndicator;

public class FluentProgressIndicator extends ProgressIndicator implements IFluentProgressIndicator<FluentProgressIndicator>{

	public FluentProgressIndicator() {
		super();
	}

	public FluentProgressIndicator(double value) {
		super(value);
	}

	@Override
	public FluentProgressIndicator getThis() {
		return this;
	}

}
