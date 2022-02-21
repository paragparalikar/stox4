package com.stox.charting.unit.parent;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;

public class PolylineUnitParent implements UnitParent<Point2D> {

	private final Polyline line;

	public PolylineUnitParent(Polyline line) {
		super();
		this.line = line;
	}

	@Override
	public Polyline getNode() {
		return line;
	}
	
	@Override
	public boolean isEmpty() {
		return line.getPoints().isEmpty();
	}

	@Override
	public void clear() {
		line.getPoints().clear();
	}

	@Override
	public void add(Point2D child) {
		line.getPoints().addAll(child.getX(), child.getY());
	}

	@Override
	public void remove(Point2D child) {
		line.getPoints().removeAll(child.getX(), child.getY());
	}

	@Override
	public void addAll(Point2D... children) {
		for (final Point2D child : children) {
			line.getPoints().addAll(child.getX(), child.getY());
		}
	}

	@Override
	public void removeAll(Point2D... children) {
		for (final Point2D child : children) {
			line.getPoints().removeAll(child.getX(), child.getY());
		}
	}

}
