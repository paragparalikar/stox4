package com.stox.common.ui;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class NoLayoutPane extends Pane {
	
	public NoLayoutPane(Node...children) {
		getChildren().addAll(children);
	}

	@Override
	protected void layoutChildren() {

	}
	
}
