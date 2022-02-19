package com.stox.charting.chart;

import javafx.scene.Node;

public interface PlotInfo<T> {

	Node getNode();
	
	void show(T model);
	
}
