package com.stox.common.ui;

import javafx.scene.Node;

public interface ConfigView<C> {

	void updateConfig(C config);
	
	void updateView(C config);
	
	Node getNode();
	
}
