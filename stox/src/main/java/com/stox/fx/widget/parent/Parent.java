package com.stox.fx.widget.parent;

import javafx.scene.Node;

public interface Parent<C>{

	void clear();
	
	boolean isEmpty();
	
	void add(C child);
	
	Node getNode();
	
	@SuppressWarnings("unchecked")
	void addAll(C...children);
	
	void remove(C child);
	
	@SuppressWarnings("unchecked")
	void removeAll(C...children);
	
}
