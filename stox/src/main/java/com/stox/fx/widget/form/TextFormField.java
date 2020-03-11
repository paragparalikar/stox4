package com.stox.fx.widget.form;

import com.stox.fx.fluent.scene.control.FluentTextField;

public class TextFormField extends FormField<String, TextFormField> {
	
	private final FluentTextField textField = new FluentTextField().fullArea();
	
	public TextFormField() {
		widget(textField);
	}
	
	@Override
	protected TextFormField getThis() {
		return this;
	}

	@Override
	public String value() {
		return textField.text();
	}

	@Override
	public TextFormField value(String value) {
		textField.text(value);
		return this;
	}

}
