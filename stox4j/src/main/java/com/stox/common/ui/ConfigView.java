package com.stox.common.ui;

import javafx.scene.Node;

public interface ConfigView {

	void populateView();
	
	void populateModel();
	
	Node getNode();
	
}
