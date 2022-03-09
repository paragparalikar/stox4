package com.stox.common.ui;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Fx {

	public void run(Runnable runnable) {
		if(null != runnable) {
			if(Platform.isFxApplicationThread()) {
				runnable.run();
			} else {
				Platform.runLater(runnable);
			}
		}
	}

	public Node icon(String value) {
		final Label label = new Label(value);
		label.getStyleClass().add("icon");
		return label;
	}
	
}
