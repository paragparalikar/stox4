package com.stox.persistence.store;

public interface Store<T> {
	
	T read();
	
	T write(T data);

}
