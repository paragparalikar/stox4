package com.stox.common.ui;

import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class DefaultDialogx extends Dialogx {

	public DefaultDialogx withTitle(String value) {
		final Label titleLabel = new Label(value);
		titleLabel.getStyleClass().add("title-label");
		getTitleBar().getLeftItems().getChildren().add(titleLabel);
		return this;
	}
	
	public DefaultDialogx withContent(Node node) {
		setContent(node);
		return this;
	}
	
	public DefaultDialogx withButton(ButtonType buttonType) {
		return withButton(buttonType, null);
	}
	
	public DefaultDialogx withButton(ButtonType buttonType, Runnable runnable) {
		final Button button = getButtonBar().create(buttonType);
		button.setOnAction(event -> {
			Optional.ofNullable(runnable).ifPresent(Runnable::run);
			hide();
		});
		return this;
	}

}
