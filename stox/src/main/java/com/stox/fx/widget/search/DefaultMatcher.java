package com.stox.fx.widget.search;

import java.util.function.BiPredicate;

public class DefaultMatcher<T> implements BiPredicate<T, String> {

	@Override
	public boolean test(final T item, final String text) {
		return String.valueOf(item).toLowerCase().contains(String.valueOf(text).toLowerCase());
	}

}