package com.stox.common.ui;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.common.event.MessageEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MessagePanel extends HBox {
	
	private final Label graphics = new Label();
	private final Label messageLabel = new Label();
	
	public MessagePanel(EventBus eventBus) {
		eventBus.register(this);
		graphics.getStyleClass().add("icon");
		messageLabel.setGraphic(graphics);
		getChildren().add(messageLabel);
	}

	@Subscribe
	public void onMessage(MessageEvent event) {
		Fx.run(() -> {
			graphics.setText(event.getIcon());
			messageLabel.setText(event.getText());
		});
	}
}
