package com.stox.fx.fluent.scene.control;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBoxBase;

public interface IFluentComboBoxBase<T, V extends ComboBoxBase<T> & IFluentComboBoxBase<T, V>> extends IFluentControl<V> {


	default T value() {
		return getThis().getValue();
	}
	
	default V value(T value) {
		getThis().setValue(value);
		return getThis();
	}

	default V editable(boolean value) {
		getThis().setEditable(value);
		return getThis();
	}

	default V promptText(String value) {
		getThis().setPromptText(value);
		return getThis();
	}

	default V onAction(EventHandler<ActionEvent> value) {
		getThis().setOnAction(value);
		return getThis();
	}

	default V onShowing(EventHandler<Event> value) {
		getThis().setOnShowing(value);
		return getThis();
	}

	default V onShown(EventHandler<Event> value) {
		getThis().onShown(value);
		return getThis();
	}

	default V onHiding(EventHandler<Event> value) {
		getThis().setOnHiding(value);
		return getThis();
	}

	default V onHidden(EventHandler<Event> value) {
		getThis().setOnHidden(value);
		return getThis();
	}
}
