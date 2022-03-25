package com.stox.charting.unit;

import org.ta4j.core.BarSeries;
import org.ta4j.core.num.Num;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.unit.parent.UnitParent;

import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import lombok.Setter;

@Setter
public class BooleanUnit extends Polygon implements Unit<Boolean, Node> {
	private static final double GAP = 10;

	private XAxis xAxis;
	private YAxis yAxis;
	private ChartingContext context;

	@Override
	public void layoutChildren(int index, Boolean model, UnitParent<Node> parent) {
		getPoints().clear();
		final BarSeries barSeries = context.getBarSeriesProperty().get();
		if(null != barSeries && index < barSeries.getBarCount() && model) {
			final Num low = barSeries.getBar(index).getLowPrice();
			final double x = xAxis.getX(index);
			final double y = yAxis.getY(low.doubleValue()) + GAP;
			final double half = 5; //xAxis.getUnitWidth() / 2;
			getPoints().addAll(x, y, x + half, y + half, x - half, y + half);
			parent.add(this);
		}
	}

}
