package com.stox.fx.fluent.scene.layout;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class FluentBorderPane extends BorderPane implements IFluentBorderPane<FluentBorderPane> {

	public FluentBorderPane() {
		super();
	}

	public FluentBorderPane(Node center, Node top, Node right, Node bottom, Node left) {
		super(center, top, right, bottom, left);
	}

	public FluentBorderPane(Node center) {
		super(center);
	}

	public FluentBorderPane clear() {
		return center(null).top(null).right(null).bottom(null).left(null);
	}

	@Override
	public FluentBorderPane getThis() {
		return this;
	}

	
}
