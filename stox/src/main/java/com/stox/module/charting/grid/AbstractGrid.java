package com.stox.module.charting.grid;


import com.stox.fx.fluent.scene.layout.FluentGroup;
import com.stox.fx.widget.Ui;

import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public abstract class AbstractGrid extends FluentGroup {
	private static final Color COLOR = Color.web("#efefef");


	public AbstractGrid() {
		managed(false).autoSizeChildren(false).classes("grid");
		
	}

	@Override
	protected void layoutChildren() {

	};

	public void reset() {
		getChildren().clear();
	}

	public void addLine(double value) {
		final Parent parent = getParent();
		if (null != parent && parent instanceof Region) {
			final Region region = (Region) parent;
			value = Ui.px(value);
			addLine(value, region);
		}
	}

	protected abstract void addLine(final double value, final Region parent);

	protected void addLine(final double startX, final double startY, final double endX, final double endY) {
		final Line line = new Line(startX, startY, endX, endY);
		line.setStroke(COLOR);
		getChildren().add(line);
	}

}
