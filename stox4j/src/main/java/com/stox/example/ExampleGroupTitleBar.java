package com.stox.example;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ExampleGroupTitleBar extends VBox {

	public ExampleGroupTitleBar(ExampleGroupComboBox comboBox, ExampleGroupControlsMenuButton menuButton) {
		HBox.setHgrow(comboBox, Priority.ALWAYS);
		comboBox.setMaxWidth(Double.MAX_VALUE);
		getChildren().add(new HBox(comboBox, menuButton));
	}
	
}
