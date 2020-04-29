package com.stox.module.core.model.intf;

public interface HasId<T> {

	T id();
	
	HasId<T> id(T id);
	
}
