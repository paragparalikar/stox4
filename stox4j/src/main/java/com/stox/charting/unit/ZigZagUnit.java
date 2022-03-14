package com.stox.charting.unit;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.indicator.Move;

import javafx.geometry.Point2D;
import lombok.Setter;

@Setter
public class ZigZagUnit implements Unit<Move, Point2D> {

	private XAxis xAxis;
	private YAxis yAxis;
	private ChartingContext context;

	@Override
	public void layoutChildren(int index, Move model, UnitParent<Point2D> parent) {
		if(!model.isEmpty()) {
			parent.add(new Point2D(xAxis.getX(model.getStartIndex()), yAxis.getY(model.getStartPrice().doubleValue())));
			parent.add(new Point2D(xAxis.getX(model.getEndIndex()), yAxis.getY(model.getEndPrice().doubleValue())));
		}
	}
}