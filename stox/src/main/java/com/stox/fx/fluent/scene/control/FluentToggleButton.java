package com.stox.fx.fluent.scene.control;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class FluentToggleButton extends ToggleButton implements IFluentButtonBase<FluentToggleButton> {

	public FluentToggleButton() {
		super();
	}

	public FluentToggleButton(String text, Node graphic) {
		super(text, graphic);
	}

	public FluentToggleButton(String text) {
		super(text);
	}

	@Override
	public FluentToggleButton getThis() {
		return this;
	}

	public FluentToggleButton selected(boolean value) {
		setSelected(value);
		return this;
	}

	public FluentToggleButton toggleGroup(ToggleGroup value) {
		setToggleGroup(value);
		return this;
	}

}
