package com.stox.util.collection.listenable;

import java.util.Collection;

public interface Listener<E> {

	void clear();
	
	void add(E item);
	
	void retainAll(Collection<E> items);
	
}
