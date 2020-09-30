package com.stox.util.collection.lazy;

import java.util.Set;
import java.util.function.Supplier;

public class LazySet<E> extends LazyCollection<E> implements Set<E> {

	public LazySet(Supplier<? extends Set<E>> setSupplier) {
		super(setSupplier);
	}

}
