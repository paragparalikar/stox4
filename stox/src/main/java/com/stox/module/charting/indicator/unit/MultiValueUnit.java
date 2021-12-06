package com.stox.module.charting.indicator.unit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.unit.Unit;
import com.stox.module.indicator.model.MultiValue;

import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MultiValueUnit implements Unit<MultiValue> {

	private final Parent<List<Point2D>> parent;
	
	@Override
	public void update(int index, MultiValue model, MultiValue previousModel, XAxis xAxis, YAxis yAxis) {
		final double x = xAxis.getX(index) + xAxis.getUnitWidth();
		final List<Point2D> points = Arrays.stream(model.getValues())
			.map(yAxis::getY)
			.mapToObj(y -> new Point2D(x, y))
			.collect(Collectors.toList());
		parent.add(points);
	}

}
