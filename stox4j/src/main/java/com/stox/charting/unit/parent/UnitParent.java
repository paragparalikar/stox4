package com.stox.charting.unit.parent;

import javafx.scene.Node;

public interface UnitParent<T> {

	void clear();
	Node getNode();
	boolean isEmpty();
	void add(T child);
	void remove(T child);
	public void addAll(T... children);
	public void removeAll(T... children);
	
	default void preLayoutChartChildren() {}
	default void postLayoutChartChildren() {}

}
