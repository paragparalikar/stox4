package com.stox.charting.unit;

import org.ta4j.core.num.Num;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class BooleanUnit extends Polygon implements Unit<Boolean> {
	private static final double GAP = 10;

	private final ChartingContext context;
	
	public BooleanUnit(ChartingContext context) {
		this.context = context;
		setFill(Color.BLUE);
	}

	@Override
	public Node asNode() {
		return this;
	}

	@Override
	public void layoutChildren(int index, Boolean model, XAxis xAxis, YAxis yAxis) {
		getPoints().clear();
		if(null != context.getBarSeries() && index < context.getBarSeries().getBarCount()) {
			final Num low = context.getBarSeries().getBar(index).getLowPrice();
			final double x = xAxis.getX(index);
			final double y = yAxis.getY(low) + GAP;
			final double half = xAxis.getUnitWidth() / 2;
			getPoints().addAll(x, y, x + half, half, x - half, half);
		}
	}

}
