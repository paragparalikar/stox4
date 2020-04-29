package com.stox.fx.widget.auto;

import com.stox.fx.fluent.scene.layout.IFluentVBox;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class AutoVBox extends VBox implements AutoWidget, IFluentVBox<AutoVBox> {

	public AutoVBox() {
		super();
	}

	public AutoVBox(double spacing, Node... children) {
		super(spacing, children);
	}

	public AutoVBox(double spacing) {
		super(spacing);
	}

	public AutoVBox(Node... children) {
		super(children);
	}

	@Override
	public AutoVBox getThis() {
		return this;
	}

	@Override
	public void populateView() {
		for (Node node : children()) {
			if (node instanceof AutoWidget) {
				final AutoWidget autoWidget = (AutoWidget) node;
				autoWidget.populateView();
			}
		}
	}

	@Override
	public void populateModel() {
		for (Node node : children()) {
			if (node instanceof AutoWidget) {
				final AutoWidget autoWidget = (AutoWidget) node;
				autoWidget.populateModel();
			}
		}
	}

}
