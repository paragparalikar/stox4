package com.stox.charting.unit;

import org.ta4j.core.num.Num;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.unit.parent.UnitParent;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import lombok.Setter;

@Setter
public class BarUnit implements Unit<Num, Node> {

	private XAxis xAxis;
	private YAxis yAxis;
	private ChartingContext context;
	private final Rectangle body = new Rectangle();
	
	@Override
	public void layoutChildren(int index, Num model, UnitParent<Node> parent) {
		final double barWidth = xAxis.getUnitWidth() * 0.8;
		body.setX(xAxis.getX(index) - barWidth/2d);
		body.setWidth(barWidth);
		body.setY(yAxis.getY(model.doubleValue()));
		body.setHeight(yAxis.getY(0));
		parent.add(body);
	}

}
