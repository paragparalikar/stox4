package com.stox.fx.widget.auto;

import java.lang.reflect.Field;

import com.stox.fx.fluent.scene.control.IFluentCheckBox;

import javafx.scene.control.CheckBox;

public class AutoCheckBox extends CheckBox implements AutoWidget, IFluentCheckBox<AutoCheckBox> {

	private final Field field;
	private final Object model;

	public AutoCheckBox(Field field, Object model) {
		if(!Boolean.class.equals(field.getType()) && !boolean.class.equals(field.getType())){
			throw new IllegalArgumentException("Field is not of type boolean");
		}
		this.field = field;
		field.setAccessible(true);
		this.model = model;
		classes("auto-widget");
	}

	@Override
	public AutoCheckBox getThis() {
		return this;
	}

	@Override
	public void populateView() {
		try {
			final Boolean value = field.getBoolean(model);
			setSelected(null == value ? false : value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void populateModel() {
		try {
			field.set(model, isSelected());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
