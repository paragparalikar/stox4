package com.stox.module.charting.unit.parent;

import com.stox.fx.widget.NoLayoutPane;
import com.stox.fx.widget.Ui;
import com.stox.fx.widget.parent.PolygonParentAdapter;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolygonUnitParent extends PolygonParentAdapter implements UnitParent<Point2D> {

	public PolygonUnitParent(Polygon area) {
		super(area);
	}

	@Override
	public void preLayoutChartChildren(final XAxis xAxis, final YAxis yAxis) {
		clear();
	}

	@Override
	public void postLayoutChartChildren(final XAxis xAxis, final YAxis yAxis) {
		final Parent parent = Ui.getParentOfType(NoLayoutPane.class, getNode());
		final ObservableList<Double> points = getNode().getPoints();
		if (null != parent && parent instanceof Region && 2 <= points.size()) {
			final Region region = (Region) parent;
			points.addAll(points.get(points.size() - 2), region.getHeight());
			points.addAll(points.get(0), region.getHeight());
		}
	}

	@Override
	public void unbindColorProperty() {
		getNode().fillProperty().unbind();
	}

	@Override
	public void bindColorProperty(ObjectProperty<Color> colorProperty) {
		getNode().fillProperty().bind(colorProperty);
	}

}
