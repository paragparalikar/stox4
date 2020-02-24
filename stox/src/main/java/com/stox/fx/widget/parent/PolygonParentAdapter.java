package com.stox.fx.widget.parent;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class PolygonParentAdapter implements Parent<Point2D> {

	private final Polygon area;

	public PolygonParentAdapter(Polygon area) {
		super();
		this.area = area;
	}

	@Override
	public Polygon getNode() {
		return area;
	}

	@Override
	public boolean isEmpty() {
		return area.getPoints().isEmpty();
	}
	
	@Override
	public void clear() {
		area.getPoints().clear();
	}

	@Override
	public void add(Point2D child) {
		area.getPoints().addAll(child.getX(), child.getY());
	}

	@Override
	public void remove(Point2D child) {
		area.getPoints().removeAll(child.getX(), child.getY());
	}

	@Override
	public void addAll(Point2D... children) {
		for (final Point2D child : children) {
			area.getPoints().addAll(child.getX(), child.getY());
		}
	}

	@Override
	public void removeAll(Point2D... children) {
		for (final Point2D child : children) {
			area.getPoints().removeAll(child.getX(), child.getY());
		}
	}

}
