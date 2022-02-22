package com.stox.common.ui.form.auto.view;

import javafx.scene.Node;

public interface AutoFormFieldView<T> {

	public Node getWidget();
	
	public T getValue();
	
	public void setValue(T value);
	
}
