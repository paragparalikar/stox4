package com.stox.common.ui.form.auto.view;

import javafx.scene.Node;
import javafx.scene.control.TextField;

public class DoubleFieldView implements AutoFormFieldView<Double> {

	private final TextField textField = new TextField();
	
	@Override
	public Node getWidget() {
		return textField;
	}

	@Override
	public Double getValue() {
		final String text = textField.getText();
		return null == text || 0 == text.trim().length() ? null : Double.parseDouble(text);
	}

	@Override
	public void setValue(Double value) {
		textField.setText(null == value ? null : value.toString());
	}

}
