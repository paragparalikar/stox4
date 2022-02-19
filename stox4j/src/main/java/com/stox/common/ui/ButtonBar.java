package com.stox.common.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class ButtonBar extends TitleBar {
	
	public ButtonBar() {
		getStyleClass().add("button-bar");
	}

	public Button create(ButtonType buttonType) {
		final Button button = new Button(buttonType.getText());
		getRightItems().getChildren().add(button);
		return button;
	}
	
}
