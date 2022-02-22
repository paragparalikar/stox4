package com.stox.common.ui.form.auto;

import java.lang.reflect.Field;

import com.stox.common.ui.form.FormField;
import com.stox.common.ui.form.auto.view.AutoFormFieldView;

import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;

@Builder
public class AutoFormField extends FormField {
	
	@NonNull private final Field field;
	@NonNull private final Object model;
	@NonNull private final AutoFormFieldView view;

	@SneakyThrows
	public FormField populateView() {
		withWidget(view.getWidget());
		view.setValue(field.get(model));
		return this;
	}
	
	@SneakyThrows
	public FormField populateModel() {
		field.setAccessible(true);
		field.set(model, view.getValue());
		return this;
	}

}
