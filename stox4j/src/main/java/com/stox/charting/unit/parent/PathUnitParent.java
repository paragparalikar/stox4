package com.stox.charting.unit.parent;

import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;

public class PathUnitParent implements UnitParent<PathElement> {

	private final Path path;
	
	public PathUnitParent(Path path) {
		super();
		this.path = path;
	}

	@Override
	public Path getNode() {
		return path;
	}
	
	@Override
	public boolean isEmpty() {
		return path.getElements().isEmpty();
	}

	@Override
	public void clear() {
		path.getElements().clear();
	}
	
	@Override
	public void preLayoutChartChildren() {

	}

	@Override
	public void add(PathElement child) {
		if(path.getElements().isEmpty() && child instanceof LineTo) {
			final LineTo lineTo = LineTo.class.cast(child);
			final MoveTo moveTo = new MoveTo(lineTo.getX(), lineTo.getY());
			path.getElements().add(moveTo);
		} else {
			path.getElements().add(child);
		}
	}
	
	@Override
	public void postLayoutChartChildren() {
		path.getElements().add(new ClosePath());
	}

	@Override
	public void remove(PathElement child) {
		path.getElements().remove(child);
	}

	@Override
	public void addAll(PathElement... children) {
		path.getElements().addAll(children);
	}

	@Override
	public void removeAll(PathElement... children) {
		path.getElements().removeAll(children);
	}

}
