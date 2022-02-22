package com.stox.common.ui.form.auto;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.stox.common.ui.form.Form;
import com.stox.common.ui.form.auto.view.AutoFormFieldView;
import com.stox.common.ui.form.auto.view.CheckBoxView;
import com.stox.common.ui.form.auto.view.ChoiceBoxView;
import com.stox.common.ui.form.auto.view.DoubleFieldView;
import com.stox.common.ui.form.auto.view.IntegerFieldView;

import lombok.Getter;

public class AutoForm extends Form {
	
	@Getter private final Object model;
	private final List<AutoFormField> fields = new LinkedList<>();

	public AutoForm(final Object model) {
		this.model = model;
		build();
		populateView();
	}
	
	private void build() {
		final Field[] fields = model.getClass().getDeclaredFields();
		for(Field field : fields) {
			final Class<?> type = field.getType();
			AutoFormFieldView<?> view = null;
			if (type.equals(boolean.class) || type.equals(Boolean.class)) {
				view = new CheckBoxView();
			} else if(type.equals(double.class) || type.equals(Double.class)) {
				view = new DoubleFieldView();
			} else if(type.equals(int.class) || type.equals(Integer.class)) {
				view = new IntegerFieldView();
			} else if(type.isEnum()) {
				view = new ChoiceBoxView(type.getEnumConstants());
			}
			final AutoFormField autoFormField = new AutoFormField(field, model, view);
			getChildren().add(autoFormField);
			this.fields.add(autoFormField);
		}
	}
	
	public AutoForm populateView() {
		fields.forEach(AutoFormField::populateView);
		return this;
	}
	
	public AutoForm populateModel() {
		fields.forEach(AutoFormField::populateModel);
		return this;
	}

}
