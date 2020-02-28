package com.stox.module.charting.drawing;

import com.stox.fx.widget.HasNode;
import com.stox.module.charting.axis.Updatable;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;

import javafx.scene.Node;

public interface Drawing extends Updatable, HasNode<Node> {
	
	String getType();

	void layoutChartChildren(final XAxis xAxis, final YAxis yAxis);
}
