package com.stox.fx.fluent.scene.control;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class FluentTabPane extends TabPane implements IFluentTabPane<FluentTabPane> {

	public FluentTabPane() {
	}

	public FluentTabPane(Tab... tabs) {
		super(tabs);
	}

	@Override
	public FluentTabPane getThis() {
		return this;
	}

}
