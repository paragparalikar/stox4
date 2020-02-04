package com.stox.fx.fluent.scene.control;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class FluentLabel extends Label implements IFluentLabeled<FluentLabel> {

	public FluentLabel() {
		super();
	}

	public FluentLabel(String text, Node graphic) {
		super(text, graphic);
	}

	public FluentLabel(String text) {
		super(text);
	}

	@Override
	public FluentLabel getThis() {
		return this;
	}

	public FluentLabel labelFor(Node value) {
		setLabelFor(value);
		return this;
	}

}
