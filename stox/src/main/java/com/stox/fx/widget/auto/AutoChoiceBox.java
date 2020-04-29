package com.stox.fx.widget.auto;

import java.lang.reflect.Field;

import com.stox.fx.fluent.scene.control.IFluentChoiceBox;

import javafx.scene.control.ChoiceBox;

public class AutoChoiceBox<T> extends ChoiceBox<T> implements AutoWidget, IFluentChoiceBox<T, AutoChoiceBox<T>> {

	private final Field field;
	private final Object model;

	public AutoChoiceBox(Field field, Object model) {
		this.field = field;
		this.model = model;
		classes("auto-widget");
		field.setAccessible(true);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void populateView() {
		try {
			setValue((T) field.get(model));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void populateModel() {
		try{
			field.set(model, getValue());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public AutoChoiceBox<T> getThis() {
		return this;
	}

}
