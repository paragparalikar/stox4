package com.stox.module.charting.screen.unit;

import com.stox.module.charting.screen.ScreenMatch;
import com.stox.module.charting.unit.Unit;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;

public abstract class AbstractScreenUnit implements Unit<ScreenMatch> {

	private final Group parent;
	private final Polygon node;

	public AbstractScreenUnit(final String icon, final String style, final Group parent) {
		this.parent = parent;
		node = new Polygon();
		node.getStyleClass().addAll("screen-unit", style);
	}
	
	@Override
	public void attach() {
		parent.getChildren().add(node);
	}
	
	@Override
	public void detach() {
		parent.getChildren().remove(node);
	}

	public Polygon getNode() {
		return node;
	}

}
