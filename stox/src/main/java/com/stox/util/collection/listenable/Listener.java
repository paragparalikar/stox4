package com.stox.util.collection.listenable;

public interface Listener<E> {

	void truncate();
	
	void append(E item);
	
	void rewrite();
	
	void read();
	
}
