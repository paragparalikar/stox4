package com.stox.fx.widget.auto;

import java.lang.reflect.Field;

import com.stox.fx.fluent.scene.control.IFluentTextInputControl;
import com.stox.util.Strings;

import javafx.scene.control.TextField;

public class AutoTextField extends TextField implements AutoWidget, IFluentTextInputControl<AutoTextField> {

	private final Field field;
	private final Object model;

	public AutoTextField(Field field, Object target) {
		this.field = field;
		field.setAccessible(true);
		this.model = target;
		classes("auto-widget");
	}

	@Override
	public AutoTextField getThis() {
		return this;
	}

	@Override
	public void populateView() {
		try {
			final Object value = field.get(model);
			setText(null == value ? null : value.toString());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void populateModel() {
		final String text = getText();
		final Class<?> type = field.getType();
		try {
			if (int.class.equals(type) || Integer.class.equals(type)) {
				field.set(model, Strings.hasText(text) ? Integer.parseInt(text) : 0);
			} else if (double.class.equals(type) || Double.class.equals(type)) {
				field.set(model, Strings.hasText(text) ? Double.parseDouble(text) : 0);
			} else if (long.class.equals(type) || Long.class.equals(type)) {
				field.set(model, Strings.hasText(text) ? Long.parseLong(text) : 0);
			} else if (short.class.equals(type) || Short.class.equals(type)) {
				field.set(model, Strings.hasText(text) ? Short.parseShort(text) : 0);
			} else if (String.class.equals(type)) {
				field.set(model, text);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
