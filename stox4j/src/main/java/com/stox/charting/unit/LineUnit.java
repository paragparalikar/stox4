package com.stox.charting.unit;

import org.ta4j.core.num.Num;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.unit.parent.UnitParent;

import javafx.geometry.Point2D;
import lombok.Setter;

@Setter
public class LineUnit implements Unit<Num, Point2D> {

	private XAxis xAxis;
	private YAxis yAxis;
	private ChartingContext context;
	
	@Override
	public void layoutChildren(int index, Num model, UnitParent<Point2D> parent) {
		parent.add(new Point2D(xAxis.getX(index), yAxis.getY(model.doubleValue())));
	}

}
