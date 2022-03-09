package com.stox.common.ui.modal;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class TitleBar extends HBox {

	private final HBox leftItems = new HBox();
	private final HBox centerItems = new HBox();
	private final HBox rightItems = new HBox();

	public TitleBar() {
		getStyleClass().add("title-bar");
		leftItems.setAlignment(Pos.CENTER_LEFT);
		centerItems.setAlignment(Pos.CENTER);
		rightItems.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(centerItems, Priority.ALWAYS);
		getChildren().addAll(leftItems, centerItems, rightItems);
	}

}
