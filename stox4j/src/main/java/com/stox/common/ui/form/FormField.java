package com.stox.common.ui.form;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FormField extends VBox {

	private final Label nameLabel = new Label();
	private final Pane widgetHolder = new Pane();
	
	public FormField() {
		getStyleClass().add("field");
		nameLabel.getStyleClass().add("name");
		widgetHolder.getStyleClass().add("widget-wrapper");
		getChildren().addAll(nameLabel, widgetHolder);
	}
	
	public FormField withName(String name) {
		nameLabel.setText(name);
		return this;
	}

	public FormField withWidget(Node widget) {
		widgetHolder.getChildren().clear();
		widgetHolder.getChildren().add(widget);
		return this;
	}
}
