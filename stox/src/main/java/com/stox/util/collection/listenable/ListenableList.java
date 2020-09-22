package com.stox.util.collection.listenable;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;

public class ListenableList<E> extends ListenableCollection<E> implements List<E> {
	
	private final List<E> delegate;
	
	public ListenableList(List<E> delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return retainAll(delegate.addAll(c));
	}

	@Override
	public E get(int index) {
		return delegate.get(index);
	}

	@Override
	public E set(int index, E element) {
		final E item = delegate.set(index, element);
		retainAll(true);
		return item;
	}

	@Override
	public void add(int index, E element) {
		delegate.add(index, element);
		retainAll(true);
	}

	@Override
	public E remove(int index) {
		final E item = delegate.remove(index);
		retainAll(true);
		return item;
	}

	@Override
	public int indexOf(Object o) {
		return delegate.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return delegate.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return delegate.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return delegate.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return delegate.subList(fromIndex, toIndex);
	}
	
	@Override
	public void sort(Comparator<? super E> c) {
		delegate.sort(c);
		retainAll(true);
	}
	
	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		delegate.replaceAll(operator);
		retainAll(true);
	}
}
