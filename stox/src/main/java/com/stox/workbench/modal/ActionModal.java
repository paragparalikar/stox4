package com.stox.workbench.modal;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.fluent.scene.layout.FluentVBox;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public abstract class ActionModal<T extends ActionModal<T>> extends Modal<T> {

	private final FluentButton cancelButton = new FluentButton().cancelButton(true).onAction(e -> hide());
	private final FluentButton actionButton = new FluentButton().classes("success").defaultButton(true).onAction(e -> action());
	private final FluentHBox buttonBar = new FluentHBox(cancelButton, actionButton).classes("button-bar");
	private final FluentVBox container = new FluentVBox(buttonBar).classes("padded");
	
	protected abstract void action();
	
	public ActionModal() {
		super.content(container);
	}
	
	@Override
	protected T content(Node node) {
		container.children(node, buttonBar);
		return getThis();
	}

	protected T actionButtonText(final ObservableValue<String> value) {
		return bind(actionButton, value);
	}
	
	protected T cancelButtonText(final ObservableValue<String> value) {
		return bind(cancelButton, value);
	}

	private T bind(final Labeled labeled, final ObservableValue<String> value) {
		labeled.textProperty().unbind();
		labeled.setText(null);
		labeled.textProperty().bind(value);
		return getThis();
	}
}
