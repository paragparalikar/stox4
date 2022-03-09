package com.stox.common.ui.modal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class ButtonBar extends HBox {
	
	private final HBox leftItems = new HBox();
	private final HBox centerItems = new HBox();
	private final HBox rightItems = new HBox();

	public ButtonBar() {
		getStyleClass().add("button-bar");
		leftItems.setAlignment(Pos.BOTTOM_LEFT);
		centerItems.setAlignment(Pos.BOTTOM_CENTER);
		rightItems.setAlignment(Pos.BOTTOM_RIGHT);
		HBox.setHgrow(centerItems, Priority.ALWAYS);
		getChildren().addAll(leftItems, centerItems, rightItems);
	}

	public Button create(ButtonType buttonType) {
		final Button button = new Button(buttonType.getText());
		getRightItems().getChildren().add(button);
		return button;
	}
	
}
