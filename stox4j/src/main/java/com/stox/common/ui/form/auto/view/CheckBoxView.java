package com.stox.common.ui.form.auto.view;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;

public class CheckBoxView implements AutoFormFieldView<Boolean> {

	private final CheckBox checkBox = new CheckBox();

	@Override
	public Node getWidget() {
		return checkBox;
	}

	@Override
	public Boolean getValue() {
		return checkBox.isSelected();
	}

	@Override
	public void setValue(Boolean value) {
		checkBox.setSelected(value);
	}

}
