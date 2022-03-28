package com.stox.charting.drawing;

import javafx.scene.Node;

public interface Drawing<S extends DrawingState> {

	void draw();
	
	S getState();
	
	Node getNode();
	
	void moveTo(double x, double y);
}
