package com.stox.fx.widget.auto;

import com.stox.fx.fluent.scene.layout.IFluentHBox;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class AutoHBox extends HBox implements AutoWidget, IFluentHBox<AutoHBox> {

	public AutoHBox() {
		super();
	}

	public AutoHBox(double spacing, Node... children) {
		super(spacing, children);
	}

	public AutoHBox(double spacing) {
		super(spacing);
	}

	public AutoHBox(Node... children) {
		super(children);
	}

	@Override
	public AutoHBox getThis() {
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
