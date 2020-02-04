package com.stox.fx.fluent.scene.layout;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class FluentPane extends Pane implements IFluentPane<FluentPane> {

	public FluentPane() {
		super();
	}

	public FluentPane(Node... children) {
		super(children);
	}

	@Override
	public FluentPane getThis() {
		return this;
	}

}
