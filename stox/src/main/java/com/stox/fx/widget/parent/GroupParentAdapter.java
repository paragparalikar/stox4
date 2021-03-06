package com.stox.fx.widget.parent;

import javafx.scene.Group;
import javafx.scene.Node;

public class GroupParentAdapter implements Parent<Node> {

	private final Group group;
	
	public GroupParentAdapter(Group group) {
		super();
		this.group = group;
	}

	@Override
	public Group getNode() {
		return group;
	}
	
	@Override
	public boolean isEmpty() {
		return group.getChildren().isEmpty();
	}

	@Override
	public void clear() {
		group.getChildren().clear();
	}

	@Override
	public void add(Node child) {
		group.getChildren().add(child);
	}

	@Override
	public void remove(Node child) {
		group.getChildren().remove(child);
	}

	@Override
	public void addAll(Node... children) {
		group.getChildren().addAll(children);
	}

	@Override
	public void removeAll(Node... children) {
		group.getChildren().removeAll(children);
	}

}
