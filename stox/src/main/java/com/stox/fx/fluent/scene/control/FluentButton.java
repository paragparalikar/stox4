package com.stox.fx.fluent.scene.control;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class FluentButton extends Button implements IFluentButtonBase<FluentButton> {

	public FluentButton() {
		super();
	}

	public FluentButton(String text, Node graphic) {
		super(text, graphic);
	}

	public FluentButton(String text) {
		super(text);
	}

	@Override
	public FluentButton getThis() {
		return this;
	}

	public FluentButton defaultButton(boolean value) {
		setDefaultButton(value);
		return this;
	}

	public FluentButton cancelButton(boolean value) {
		setCancelButton(value);
		return this;
	}

}
