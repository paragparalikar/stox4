package com.stox.module.charting.unit.parent;

import com.stox.fx.widget.parent.PolylineParentAdapter;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class PolylineUnitParent extends PolylineParentAdapter implements UnitParent<Point2D> {

	public PolylineUnitParent(Polyline line) {
		super(line);
	}

	@Override
	public void preLayoutChartChildren(final XAxis xAxis, final YAxis yAxis) {
		clear();
	}

	@Override
	public void postLayoutChartChildren(final XAxis xAxis, final YAxis yAxis) {

	}

	@Override
	public void unbindColorProperty() {
		getNode().strokeProperty().unbind();
	}

	@Override
	public void bindColorProperty(ObjectProperty<Color> colorProperty) {
		getNode().strokeProperty().bind(colorProperty);
	}

}
