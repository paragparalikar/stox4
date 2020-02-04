package com.stox.fx.fluent.scene.control;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class FluentRadioButton extends RadioButton implements IFluentButtonBase<FluentRadioButton> {

	public FluentRadioButton() {
		super();
	}

	public FluentRadioButton(String text) {
		super(text);
	}

	@Override
	public FluentRadioButton getThis() {
		return this;
	}

	public FluentRadioButton selected(boolean value) {
		setSelected(value);
		return this;
	}

	public FluentRadioButton toggleGroup(ToggleGroup value) {
		setToggleGroup(value);
		return this;
	}

}
