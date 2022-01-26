package com.stox.common.intf;

public interface HasId<T> {

	T id();
	
	HasId<T> id(T id);
	
}
