package com.stox.util.collection.lazy;

import java.util.Iterator;
import java.util.function.Supplier;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LazyIterable<T> implements Iterable<T> {
	
	private Iterable<T> iterable;
	
	@NonNull
	private final Supplier<? extends Iterable<T>> iterableSupplier;
	
	protected Iterable<T> delegate(){
		return null == iterable ? iterable = iterableSupplier.get() : iterable;
	}

	@Override
	public Iterator<T> iterator() {
		return delegate().iterator();
	}

}
