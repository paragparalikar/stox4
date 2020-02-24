package com.stox.module.charting.unit;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.core.model.Bar;

import javafx.geometry.Point2D;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LinePriceUnit implements PriceUnit {

	private final Parent<Point2D> parent;
	private final Configuration configuration;

	@Override
	public void update(int index, Bar model, Bar previousModel, XAxis xAxis, YAxis yAxis) {
		parent.addAll(new Point2D(xAxis.getX(index) + xAxis.getUnitWidth(), yAxis.getY(model.getClose())));
	}
	
}
