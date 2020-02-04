package com.stox.fx.fluent.scene.control;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

public class FluentSplitPane extends SplitPane implements IFluentControl<FluentSplitPane> {

	public FluentSplitPane() {
		super();
	}

	public FluentSplitPane(Node... items) {
		super(items);
	}

	@Override
	public FluentSplitPane getThis() {
		return this;
	}
	
	public FluentSplitPane orientation(Orientation value){
		setOrientation(value);
		return this;
	}
	
	public FluentSplitPane deviderPosition(int dividerIndex, double position){
		setDividerPosition(dividerIndex, position);
		return this;
	}
	
	public FluentSplitPane deviderPositions(double...positions){
		setDividerPositions(positions);
		return this;
	}

}
