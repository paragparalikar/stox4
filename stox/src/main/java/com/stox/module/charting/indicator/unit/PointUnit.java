package com.stox.module.charting.indicator.unit;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.unit.Unit;

import javafx.geometry.Point2D;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PointUnit implements Unit<Double> {

	@NonNull
	private final Parent<Point2D> parent;

	@Override
	public void update(int index, Double model, Double previousModel, XAxis xAxis, YAxis yAxis) {
		if (null != model) {
			parent.add(new Point2D(xAxis.getX(index) + xAxis.getUnitWidth(), yAxis.getY(model)));
		}
	}
	
	@Override
	public void attach() {

	}

	@Override
	public void detach() {

	}

}
