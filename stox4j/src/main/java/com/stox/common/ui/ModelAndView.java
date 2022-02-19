package com.stox.common.ui;

import javafx.scene.Node;

public interface ModelAndView {

	void updateModel();
	
	void updateView();
	
	Node getNode();
	
}
