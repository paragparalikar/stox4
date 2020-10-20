package com.stox.util.collection.lazy;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;

public class LazyList<E> extends LazyCollection<E> implements List<E> {

	public LazyList(Supplier<? extends List<E>> listSupplier) {
		super(listSupplier);
	}
	
	@Override
	protected List<E> delegate() {
		return (List<E>) super.delegate();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return delegate().addAll(index, c);
	}

	@Override
	public E get(int index) {
		return delegate().get(index);
	}

	@Override
	public E set(int index, E element) {
		return delegate().set(index, element);
	}

	@Override
	public void add(int index, E element) {
		delegate().add(index, element);
	}

	@Override
	public E remove(int index) {
		return delegate().remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return delegate().indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return delegate().lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return delegate().listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return delegate().listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return delegate().subList(fromIndex, toIndex);
	}
	
	@Override
	public void sort(Comparator<? super E> c) {
		delegate().sort(c);
	}

}
