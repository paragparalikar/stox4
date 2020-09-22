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
		read();
		return rewrite(delegate.addAll(c));
	}

	@Override
	public E get(int index) {
		read();
		return delegate.get(index);
	}

	@Override
	public E set(int index, E element) {
		read();
		final E item = delegate.set(index, element);
		rewrite(true);
		return item;
	}

	@Override
	public void add(int index, E element) {
		read();
		delegate.add(index, element);
		rewrite(true);
	}

	@Override
	public E remove(int index) {
		read();
		final E item = delegate.remove(index);
		rewrite(true);
		return item;
	}

	@Override
	public int indexOf(Object o) {
		read();
		return delegate.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		read();
		return delegate.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		read();
		return delegate.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		read();
		return delegate.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		read();
		return delegate.subList(fromIndex, toIndex);
	}
	
	@Override
	public void sort(Comparator<? super E> c) {
		read();
		delegate.sort(c);
		rewrite(true);
	}
	
	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		read();
		delegate.replaceAll(operator);
		rewrite(true);
	}
}
