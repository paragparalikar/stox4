package com.stox.fx.fluent.scene.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class FluentTextField extends TextField implements IFluentTextInputControl<FluentTextField> {

	public FluentTextField() {
		super();
	}

	public FluentTextField(String text) {
		super(text);
	}

	@Override
	public FluentTextField getThis() {
		return this;
	}

	public FluentTextField onAction(EventHandler<ActionEvent> value) {
		setOnAction(value);
		return this;
	}

	public FluentTextField prefColumnCount(int value) {
		setPrefColumnCount(value);
		return this;
	}

	public FluentTextField alignment(Pos value) {
		setAlignment(value);
		return this;
	}

}
