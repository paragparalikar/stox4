package com.stox.module.charting.tools;

import com.stox.module.charting.ChartingView;

import javafx.scene.Node;

public interface ChartingToolBox {

	Node getNode();
	
	void attach(ChartingView chartingView);
	
}
