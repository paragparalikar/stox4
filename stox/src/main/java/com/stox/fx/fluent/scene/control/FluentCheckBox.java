package com.stox.fx.fluent.scene.control;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class FluentCheckBox extends CheckBox implements IFluentButtonBase<FluentCheckBox> {

	public FluentCheckBox() {
	}

	public FluentCheckBox(String text) {
		super(text);
	}

	@Override
	public FluentCheckBox getThis() {
		return this;
	}

	public FluentCheckBox selected(boolean value) {
		setSelected(value);
		return this;
	}

	public boolean selected() {
		return isSelected();
	}

	public FluentCheckBox onSelected(Runnable callback) {
		Objects.requireNonNull(callback, "callback can not be null");
		selectedProperty().addListener((o, old, value) -> {
			if (value) {
				callback.run();
			}
		});
		return this;
	}

	public FluentCheckBox onUnselected(Runnable callback) {
		Objects.requireNonNull(callback, "callback can not be null");
		selectedProperty().addListener((o, old, value) -> {
			if (!value) {
				callback.run();
			}
		});
		return this;
	}

	public FluentCheckBox indeterminate(boolean value) {
		setIndeterminate(value);
		return this;
	}

	public boolean indeterminate() {
		return isIndeterminate();
	}

	public FluentCheckBox allowIndeterminate(boolean value) {
		setAllowIndeterminate(value);
		return this;
	}

	public boolean allowIndeterminate() {
		return isAllowIndeterminate();
	}

}
