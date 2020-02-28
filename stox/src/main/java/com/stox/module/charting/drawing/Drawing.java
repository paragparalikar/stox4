package com.stox.module.charting.drawing;

import com.stox.fx.widget.HasNode;
import com.stox.module.charting.axis.Updatable;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;

import javafx.scene.Node;

public interface Drawing<S extends DrawingState> extends Updatable, HasNode<Node> {
	
	S state();
	
	Drawing<S> state(S state);
	
	void move(final double xDelta, final double yDelta);
	
	void layoutChartChildren(final XAxis xAxis, final YAxis yAxis);
}
