package com.stox.common.ui.modal;

import com.stox.common.ui.Fx;
import com.stox.common.ui.Icon;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ConfirmationModal extends Modal {
	
	private final Label content = new Label();
	private final Button noButton = new Button("No");
	private final Button yesButton = new Button("Yes");
	
	public ConfirmationModal() {
		yesButton.setGraphic(Fx.icon(Icon.CHECK));
		noButton.setGraphic(Fx.icon(Icon.TIMES));
		noButton.setOnAction(event -> hide());
		super.withButton(noButton)
			.withButton(yesButton)
			.withContent(content);
	}
	
	public ConfirmationModal withMessageIcon(String value) {
		content.setGraphic(Fx.icon(value));
		return this;
	}
	
	public ConfirmationModal withMessageText(String value) {
		content.setText(value);
		return this;
	}
	
	public ConfirmationModal withAction(Runnable value) {
		if(null != value) yesButton.setOnAction(event -> {
			value.run();
			hide();
		});
		return this;
	}
}
