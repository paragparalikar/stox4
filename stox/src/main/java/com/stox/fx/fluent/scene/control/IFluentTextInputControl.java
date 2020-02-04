package com.stox.fx.fluent.scene.control;

import javafx.scene.control.TextInputControl;

public interface IFluentTextInputControl<T extends TextInputControl & IFluentTextInputControl<T>>
		extends IFluentControl<T> {

	default String text(){
		return getThis().getText();
	}
	
	default T text(String value) {
		getThis().setText(value);
		return getThis();
	}

	default T editable(boolean value) {
		getThis().setEditable(value);
		return getThis();
	}

	default T promptText(String text) {
		getThis().setPromptText(text);
		return getThis();
	}

}
