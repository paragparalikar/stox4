package com.stox.charting.unit;

import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;

import javafx.scene.Node;
import javafx.scene.shape.Polygon;

public class BooleanUnit extends Polygon implements Unit<Boolean> {

	public BooleanUnit() {
		
	}

	@Override
	public Node asNode() {
		return this;
	}

	@Override
	public void layoutChildren(int index, Boolean model, XAxis xAxis, YAxis yAxis) {
		
	}

}
