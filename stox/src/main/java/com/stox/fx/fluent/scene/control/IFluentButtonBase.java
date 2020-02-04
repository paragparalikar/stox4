package com.stox.fx.fluent.scene.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;

public interface IFluentButtonBase<T extends ButtonBase & IFluentButtonBase<T>> extends IFluentLabeled<T> {

	default T onAction(EventHandler<ActionEvent> value) {
		getThis().setOnAction(value);
		return getThis();
	}

}
