package com.stox.fx.fluent.scene.layout;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class FluentHBox extends HBox implements IFluentHBox<FluentHBox> {

	public FluentHBox() {
	}

	public FluentHBox(double spacing) {
		super(spacing);
	}

	public FluentHBox(Node... children) {
		super(children);
	}

	public FluentHBox(double spacing, Node... children) {
		super(spacing, children);
	}

	@Override
	public FluentHBox getThis() {
		return this;
	}

}
