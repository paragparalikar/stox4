package com.stox.fx.fluent.scene.layout;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class FluentStackPane extends StackPane implements IFluentPane<FluentStackPane> {

	public FluentStackPane() {
	}

	public FluentStackPane(Node... children) {
		super(children);
	}

	@Override
	public FluentStackPane getThis() {
		return this;
	}

	public FluentStackPane alignment(Pos value) {
		setAlignment(value);
		return this;
	}

}
