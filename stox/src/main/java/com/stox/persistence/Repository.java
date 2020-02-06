package com.stox.persistence;

import java.util.List;

public interface Repository<I,T> {

	T find(I id);
	
	List<T> findAll();
	
	I save(T entity);
	
	void update(T entity);
	
	void delete(I id);
	
}
