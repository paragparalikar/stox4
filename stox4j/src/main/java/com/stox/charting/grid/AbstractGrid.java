package com.stox.charting.grid;


import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public abstract class AbstractGrid extends Group {

	public AbstractGrid() {
		setManaged(false);
		setAutoSizeChildren(false);
		getStyleClass().add("grid");
	}

	@Override
	protected void layoutChildren() {

	};

	public void reset() {
		getChildren().clear();
	}

	public Line addLine(double value) {
		final Parent parent = getParent();
		if (null != parent && parent instanceof Region) {
			final Region region = (Region) parent;
			return addLine(value, region);
		}
		return null;
	}

	protected abstract Line addLine(final double value, final Region parent);

	protected Line addLine(final double startX, final double startY, final double endX, final double endY) {
		final Line line = new Line(startX, startY, endX, endY);
		line.setStroke(Color.web("#EFEFEF"));
		line.getStyleClass().add("grid-line");
		getChildren().add(line);
		return line;
	}

}
