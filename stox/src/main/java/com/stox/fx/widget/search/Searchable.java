package com.stox.fx.widget.search;

public interface Searchable<T> {

	int size();

	T get(int index);

	int getSelectedIndex();

	void select(final int index);

	void scrollTo(final int index);

}
