package com.stox.fx.widget.parent;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class PaneParentAdapter implements Parent<Node> {

	private final Pane pane;
	
	public PaneParentAdapter(final Pane pane) {
		this.pane = pane;
	}

	@Override
	public Pane getNode() {
		return pane;
	}
	
	@Override
	public boolean isEmpty() {
		return pane.getChildren().isEmpty();
	}

	@Override
	public void clear() {
		pane.getChildren().clear();
	}

	@Override
	public void add(Node child) {
		pane.getChildren().add(child);
	}

	@Override
	public void remove(Node child) {
		pane.getChildren().remove(child);
	}

	@Override
	public void addAll(Node... children) {
		pane.getChildren().addAll(children);
	}

	@Override
	public void removeAll(Node... children) {
		pane.getChildren().removeAll(children);
	}

}
