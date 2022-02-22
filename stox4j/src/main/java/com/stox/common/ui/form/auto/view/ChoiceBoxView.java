package com.stox.common.ui.form.auto.view;

import java.util.Arrays;
import java.util.Enumeration;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxView implements AutoFormFieldView<Object> {

	private final ChoiceBox choiceBox = new ChoiceBox();
	
	public ChoiceBoxView(Object[] enumConstants) {
		choiceBox.getItems().addAll(Arrays.asList(enumConstants));
	}
	
	@Override
	public Node getWidget() {
		return choiceBox;
	}

	@Override
	public Object getValue() {
		return choiceBox.getValue();
	}

	@Override
	public void setValue(Object value) {
		if(null == value) {
			choiceBox.getSelectionModel().clearSelection();
		} else {
			choiceBox.getSelectionModel().select(value);
		}
	}

}
