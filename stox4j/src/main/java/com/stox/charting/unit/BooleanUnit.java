package com.stox.charting.unit;

import org.ta4j.core.BarSeries;
import org.ta4j.core.num.Num;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import lombok.Setter;

public class BooleanUnit extends Polygon implements Unit<Boolean> {
	private static final double GAP = 10;

	@Setter private XAxis xAxis;
	@Setter private YAxis yAxis;
	@Setter private ChartingContext context;
	
	public BooleanUnit() {
		setFill(Color.BLUE);
	}

	@Override
	public Node asNode() {
		return this;
	}

	@Override
	public void layoutChildren(int index, Boolean model) {
		getPoints().clear();
		final BarSeries barSeries = context.getBarSeriesProperty().get();
		if(null != barSeries && index < barSeries.getBarCount() && model) {
			setVisible(true);
			final Num low = barSeries.getBar(index).getLowPrice();
			final double x = xAxis.getX(index);
			final double y = yAxis.getY(low) + GAP;
			final double half = xAxis.getUnitWidth() / 2;
			getPoints().addAll(x, y, x + half, y + half, x - half, y + half);
		} else {
			setVisible(false);
		}
	}

}
