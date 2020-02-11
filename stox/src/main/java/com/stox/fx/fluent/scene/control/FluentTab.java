package com.stox.fx.fluent.scene.control;

import javafx.scene.Node;
import javafx.scene.control.Tab;

public class FluentTab extends Tab implements IFluentTab<FluentTab> {

	public FluentTab() {
	}

	public FluentTab(String text) {
		super(text);
	}

	public FluentTab(String text, Node content) {
		super(text, content);
	}

	@Override
	public FluentTab getThis() {
		return this;
	}

	
}
