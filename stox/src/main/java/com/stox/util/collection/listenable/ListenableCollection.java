package com.stox.util.collection.listenable;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListenableCollection<E> implements Collection<E>, Listenable<E>{
	
	@NonNull
	private final Collection<E> delegate;
	private final List<Listener<E>> listeners = new LinkedList<>();
	
	@Override
	public Listenable<E> addListener(Listener<E> listener){
		if(!listeners.contains(listener)) listeners.add(listener);
		return this;
	}
	
	@Override
	public Listenable<E> removeListener(Listener<E> listener){
		listeners.remove(listener);
		return this;
	}
	
	@Override
	public boolean add(E e) {
		listeners.forEach(listener -> listener.add(e));
		return delegate.add(e);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		c.forEach(e -> listeners.forEach(listener -> listener.add(e)));
		return delegate.addAll(c);
	}
	
	@Override
	public void clear() {
		listeners.forEach(listener -> listener.clear());
		delegate.clear();
	}
	
	@Override
	public boolean remove(Object o) {
		return retainAll(delegate.remove(o));
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return retainAll(delegate.removeAll(c));
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return retainAll(delegate.removeIf(filter));
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return retainAll(delegate.retainAll(c));
	}
	
	protected boolean retainAll(final boolean result) {
		if(result) {
			final Collection<E> unmodifiable = Collections.unmodifiableCollection(delegate);
			listeners.forEach(listener -> listener.retainAll(unmodifiable));
		}
		return result;
	}

	public void forEach(Consumer<? super E> action) {
		delegate.forEach(action);
	}

	public int size() {
		return delegate.size();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	public Iterator<E> iterator() {
		return delegate.iterator();
	}

	public Object[] toArray() {
		return delegate.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}

	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public Spliterator<E> spliterator() {
		return delegate.spliterator();
	}

	public Stream<E> stream() {
		return delegate.stream();
	}

	public Stream<E> parallelStream() {
		return delegate.parallelStream();
	}

}


