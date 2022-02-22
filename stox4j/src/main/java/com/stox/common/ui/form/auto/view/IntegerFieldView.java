package com.stox.common.ui.form.auto.view;

import javafx.scene.Node;
import javafx.scene.control.TextField;

public class IntegerFieldView implements AutoFormFieldView<Integer> {

	private final TextField textField = new TextField();
	
	@Override
	public Node getWidget() {
		return textField;
	}

	@Override
	public Integer getValue() {
		final String text = textField.getText();
		return null == text || 0 == text.trim().length() ? null : Integer.parseInt(text);
	}

	@Override
	public void setValue(Integer value) {
		textField.setText(null == value ? null : value.toString());
	}

}
