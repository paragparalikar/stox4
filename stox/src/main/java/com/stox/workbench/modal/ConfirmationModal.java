package com.stox.workbench.modal;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.widget.Icon;
import com.stox.module.core.model.intf.Action;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class ConfirmationModal extends ActionModal<ConfirmationModal> {

	private final Action action;
	private final FluentLabel messageLabel = new FluentLabel();
	
	public ConfirmationModal(@NonNull final Action action) {
		this.action = action;
		danger().graphic(Icon.WARNING).content(messageLabel);
	}
	
	@Override
	public ConfirmationModal title(final ObservableValue<String> titleValue) {
		return super.title(titleValue);
	}
	
	public ConfirmationModal message(final ObservableValue<String> messageValue) {
		messageLabel.textProperty().unbind();
		messageLabel.text(null);
		messageLabel.textProperty().bind(messageValue);
		return this;
	}
	
	@Override
	public ConfirmationModal actionButtonText(ObservableValue<String> value) {
		return super.actionButtonText(value);
	}
	
	@Override
	public ConfirmationModal cancelButtonText(ObservableValue<String> value) {
		return super.cancelButtonText(value);
	}
	
	@Override
	protected void action() {
		action.run();
		hide();
	}

	@Override
	protected ConfirmationModal getThis() {
		return this;
	}

}
