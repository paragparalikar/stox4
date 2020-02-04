package com.stox.fx.fluent.scene.control;

import javafx.scene.control.ProgressBar;

public class FluentProgressBar extends ProgressBar implements IFluentProgressIndicator<FluentProgressBar> {

	public FluentProgressBar() {
	}

	public FluentProgressBar(double progress) {
		super(progress);
	}

	@Override
	public FluentProgressBar getThis() {
		return this;
	}

}
