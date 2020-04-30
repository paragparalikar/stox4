package com.stox.module.charting.indicator.unit;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.unit.Unit;
import com.stox.module.core.model.Bar;
import com.stox.module.core.model.Move;

import javafx.geometry.Point2D;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MoveUnit implements Unit<Move> {

	@NonNull
	private final Parent<Point2D> parent;

	@Override
	public void update(int index, Move model, Move previousModel, XAxis xAxis, YAxis yAxis) {
		if (null != model) {
			if (parent.isEmpty()) {
				final Bar start = model.start();
				parent.add(new Point2D(getX(model.startIndex(), xAxis), yAxis.getY(model.barValue().resolve(start))));
			}
			final Bar end = model.end();
			parent.add(new Point2D(getX(model.endIndex(), xAxis), yAxis.getY(model.barValue().resolve(end))));
		}
	}

	private double getX(final int index, final XAxis xAxis) {
		return xAxis.getX(index) + xAxis.getUnitWidth() / 2;
	}

	@Override
	public void attach() {

	}

	@Override
	public void detach() {

	}

}
