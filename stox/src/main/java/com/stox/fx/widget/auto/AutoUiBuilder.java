package com.stox.fx.widget.auto;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.util.Strings;

import javafx.geometry.Pos;
import javafx.scene.Node;

public class AutoUiBuilder {
	
	public AutoVBox build(Object model){
		try{
			final AutoVBox autoVBox = new AutoVBox();
			final Field[] fields = model.getClass().getDeclaredFields();
			for (final Field field : fields) {
				if (!field.getType().isPrimitive() && !field.getType().isEnum()) {
					field.setAccessible(true);
					final Object composite = field.get(model);
					if(null != composite && !isJavaNative(composite.getClass().getPackage())){
						build(composite);
					}
				} else if (isAutoUiCandidate(field)) {
					autoVBox.getChildren().add(build(field, model));
				}
			}
			return autoVBox;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean isJavaNative(final Package package_) {
		return null == package_ || package_.getName().startsWith("java");
	}

	private boolean isAutoUiCandidate(final Field field) {
		final int modifiers = field.getModifiers();
		return !Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Node build(final Field field, final Object model) {
		final Class<?> type = field.getType();
		if (type.equals(boolean.class) || type.equals(Boolean.class)) {
			return build(field, new AutoCheckBox(field, model).fullWidth());
		} else if (type.isEnum()) {
			final AutoChoiceBox autoChoiceBox = new AutoChoiceBox(field, model);
			autoChoiceBox.getItems().addAll(type.getEnumConstants());
			return build(field, autoChoiceBox.fullWidth());
		} else {
			return build(field, new AutoTextField(field, model).fullWidth());
		}
	}
	
	private Node build(Field field, Node widget){
		final String label = Strings.splitCamelCase(field.getName());
		final Node widgetContainer = new AutoHBox(widget).alignment(Pos.CENTER_RIGHT).fullWidth();
		final Node fieldContainer = new AutoHBox(new FluentLabel(label), widgetContainer).alignment(Pos.CENTER_LEFT).classes("spaced", "padded").fullWidth();
		return new AutoFormField(fieldContainer).fullWidth();
	}
	
}
