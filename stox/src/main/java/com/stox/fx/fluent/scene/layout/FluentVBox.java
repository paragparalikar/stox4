package com.stox.fx.fluent.scene.layout;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class FluentVBox extends VBox implements IFluentVBox<FluentVBox> {

	public FluentVBox() {
	}

	public FluentVBox(double spacing) {
		super(spacing);
	}

	public FluentVBox(Node... children) {
		super(children);
	}

	public FluentVBox(double spacing, Node... children) {
		super(spacing, children);
	}

	@Override
	public FluentVBox getThis() {
		return this;
	}

	

}
