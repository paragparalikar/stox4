package com.stox.charting.unit.parent;

import java.util.Arrays;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class GroupUnitParent implements UnitParent<Node> {

	private final Group group;
	private Color color = Color.GRAY;
	
	public GroupUnitParent(Group group) {
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
		setColor(this.color, child);
		group.getChildren().add(child);
	}

	@Override
	public void remove(Node child) {
		group.getChildren().remove(child);
	}

	@Override
	public void addAll(Node... children) {
		group.getChildren().addAll(children);
		Arrays.asList(children).forEach(child -> setColor(this.color, child));
	}

	@Override
	public void removeAll(Node... children) {
		group.getChildren().removeAll(children);
	}
	
	@Override
	public void setColor(Color color) {
		this.color = color;
		this.group.getChildren().forEach(child -> setColor(color, child));
	}
	
	private void setColor(Color color, Node child) {
		if(child instanceof Shape) Shape.class.cast(child).setFill(color);
	}
}
