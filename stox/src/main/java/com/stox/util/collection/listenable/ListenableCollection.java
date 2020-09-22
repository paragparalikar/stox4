package com.stox.util.collection.listenable;

import java.util.Collection;
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
		read();
		listeners.forEach(listener -> listener.append(e));
		return delegate.add(e);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		read();
		c.forEach(e -> listeners.forEach(listener -> listener.append(e)));
		return delegate.addAll(c);
	}
	
	@Override
	public void clear() {
		listeners.forEach(listener -> listener.truncate());
		delegate.clear();
	}
	
	@Override
	public boolean remove(Object o) {
		read();
		return rewrite(delegate.remove(o));
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		read();
		return rewrite(delegate.removeAll(c));
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		read();
		return rewrite(delegate.removeIf(filter));
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		read();
		return rewrite(delegate.retainAll(c));
	}
	
	protected void read() {
		listeners.forEach(listener -> listener.read());
	}
	
	protected boolean rewrite(final boolean result) {
		if(result) listeners.forEach(listener -> listener.rewrite());
		return result;
	}

	public void forEach(Consumer<? super E> action) {
		read();
		delegate.forEach(action);
	}

	public int size() {
		read();
		return delegate.size();
	}

	public boolean isEmpty() {
		read();
		return delegate.isEmpty();
	}

	public boolean contains(Object o) {
		read();
		return delegate.contains(o);
	}

	public Iterator<E> iterator() {
		read();
		return delegate.iterator();
	}

	public Object[] toArray() {
		read();
		return delegate.toArray();
	}

	public <T> T[] toArray(T[] a) {
		read();
		return delegate.toArray(a);
	}

	public boolean containsAll(Collection<?> c) {
		read();
		return delegate.containsAll(c);
	}

	public boolean equals(Object o) {
		read();
		return delegate.equals(o);
	}

	public int hashCode() {
		read();
		return delegate.hashCode();
	}

	public Spliterator<E> spliterator() {
		read();
		return delegate.spliterator();
	}

	public Stream<E> stream() {
		read();
		return delegate.stream();
	}

	public Stream<E> parallelStream() {
		read();
		return delegate.parallelStream();
	}

}


