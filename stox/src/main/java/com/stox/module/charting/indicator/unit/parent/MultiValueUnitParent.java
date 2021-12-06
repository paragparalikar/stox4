package com.stox.module.charting.indicator.unit.parent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.unit.parent.UnitParent;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class MultiValueUnitParent implements UnitParent<List<Point2D>>  {
	private static final Color[] COLORS = new Color[] {Color.BLUE, Color.ORANGE, Color.GREEN};
	
	private final Group group;
	private final List<Polyline> polylines = new ArrayList<>();

	public MultiValueUnitParent(final Group group, final int valueCount) {
		this.group = group;
		IntStream.range(0, valueCount)
			.mapToObj(index -> {
				final Polyline polyline = new Polyline();
				polyline.setStroke(COLORS[index]);
				return polyline;
			})
			.forEach(polylines::add);
		group.getChildren().addAll(polylines);
	}
	
	public Polyline getPrimaryPolyline() {
		return polylines.get(0);
	}
	
	@Override
	public void clear() {
		polylines.forEach(polyline -> polyline.getPoints().clear());
	}

	@Override
	public boolean isEmpty() {
		return polylines.get(0).getPoints().isEmpty();
	}
	
	@Override
	public void preLayoutChartChildren(XAxis xAxis, YAxis yAxis) {
		clear();
	}
	
	@Override
	public Node getNode() {
		return group;
	}
	
	@Override
	public void add(List<Point2D> child) {
		if(child.size() != polylines.size()) {
			throw new IllegalArgumentException("Size of points must be equal to polylines");
		}
		for(int index = 0; index < child.size(); index++) {
			final Point2D point = child.get(index);
			final Polyline polyline = polylines.get(index);
			polyline.getPoints().addAll(point.getX(), point.getY());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addAll(List<Point2D>... children) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(List<Point2D> child) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void removeAll(List<Point2D>... children) {
		throw new UnsupportedOperationException();
	}

}
