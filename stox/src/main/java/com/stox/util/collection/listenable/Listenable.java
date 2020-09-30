package com.stox.util.collection.listenable;

public interface Listenable<E> {

	Listenable<E> addListener(Listener<E> listener);

	Listenable<E> removeListener(Listener<E> listener);

}