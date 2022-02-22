package com.stox.common.ui.form;

import javafx.scene.layout.VBox;

public class Form extends VBox {

	public Form() {
		getStyleClass().add("form");
	}
	
	public Form withField(FormField field) {
		getChildren().add(field);
		return this;
	}
	
}
