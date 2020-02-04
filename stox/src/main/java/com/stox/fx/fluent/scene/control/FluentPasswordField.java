package com.stox.fx.fluent.scene.control;

import javafx.scene.control.PasswordField;

public class FluentPasswordField extends PasswordField implements IFluentTextInputControl<FluentPasswordField> {

	@Override
	public FluentPasswordField getThis() {
		return this;
	}

}
