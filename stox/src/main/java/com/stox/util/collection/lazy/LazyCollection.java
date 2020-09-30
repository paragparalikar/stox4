package com.stox.util.collection.lazy;

import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.NonNull;

public class LazyCollection<E> extends LazyIterable<E> implements Collection<E> {

	public LazyCollection(@NonNull final Supplier<? extends Collection<E>> collectionSupplier) {
		super(collectionSupplier);
	}
	
	@Override
	protected Collection<E> delegate() {
		return (Collection<E>) super.delegate();
	}

	public void forEach(Consumer<? super E> action) {
		delegate().forEach(action);
	}

	public int size() {
		return delegate().size();
	}

	public boolean isEmpty() {
		return delegate().isEmpty();
	}

	public boolean contains(Object o) {
		return delegate().contains(o);
	}

	public Object[] toArray() {
		return delegate().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return delegate().toArray(a);
	}

	public boolean add(E e) {
		return delegate().add(e);
	}

	public boolean remove(Object o) {
		return delegate().remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return delegate().containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return delegate().addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return delegate().removeAll(c);
	}

	public boolean removeIf(Predicate<? super E> filter) {
		return delegate().removeIf(filter);
	}

	public boolean retainAll(Collection<?> c) {
		return delegate().retainAll(c);
	}

	public void clear() {
		delegate().clear();
	}

	public boolean equals(Object o) {
		return delegate().equals(o);
	}

	public int hashCode() {
		return delegate().hashCode();
	}

	public Spliterator<E> spliterator() {
		return delegate().spliterator();
	}

	public Stream<E> stream() {
		return delegate().stream();
	}

	public Stream<E> parallelStream() {
		return delegate().parallelStream();
	}

}
