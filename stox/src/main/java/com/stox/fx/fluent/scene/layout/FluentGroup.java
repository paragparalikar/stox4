package com.stox.fx.fluent.scene.layout;

import java.util.Collection;

import com.stox.fx.fluent.scene.IFluentParent;

import javafx.scene.Group;
import javafx.scene.Node;

public class FluentGroup extends Group implements IFluentParent<FluentGroup> {

	public FluentGroup() {
		super();
	}

	public FluentGroup(Collection<Node> children) {
		super(children);
	}

	public FluentGroup(Node... children) {
		super(children);
	}

	@Override
	public FluentGroup getThis() {
		return this;
	}
	
	public FluentGroup autoSizeChildren(boolean value){
		setAutoSizeChildren(value);
		return this;
	}

}
