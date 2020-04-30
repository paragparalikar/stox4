package com.stox.module.charting.indicator.unit;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.unit.Unit;
import com.stox.module.indicator.model.Channel;

import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChannelUnit implements Unit<Channel>{

	private final Parent<Point2D> parent;

	@Override
	public void attach() {
		
	}

	@Override
	public void detach() {
		
	}

	@Override
	public void update(int index, Channel model, Channel previousModel, XAxis xAxis, YAxis yAxis) {
		final double x = xAxis.getX(index) + xAxis.getUnitWidth();
		final double upper = yAxis.getY(model.upper());
		final double middle = yAxis.getY(model.middle());
		final double lower = yAxis.getY(model.lower());
		parent.addAll(new Point2D(x, upper), new Point2D(x, middle), new Point2D(x, lower));
	}
	
}
