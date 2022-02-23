package com.stox.charting.unit.parent;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public interface UnitParent<T> {

	void clear();
	Node getNode();
	boolean isEmpty();
	void add(T child);
	void remove(T child);
	void addAll(T... children);
	void removeAll(T... children);
	
	default void setColor(Color color) {}
	default void preLayoutChartChildren() {}
	default void postLayoutChartChildren() {}

}
