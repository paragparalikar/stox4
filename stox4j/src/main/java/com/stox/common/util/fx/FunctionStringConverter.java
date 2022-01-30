package com.stox.common.util.fx;

import java.util.function.Function;

import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FunctionStringConverter<T> extends StringConverter<T> {

	private final Function<T, String> toStringFunction;
	private final Function<String, T> fromStringFunction;
	
	public FunctionStringConverter(final Function<T, String> toStringFunction) {
		this(toStringFunction, null);
	}
	
	@Override
	public String toString(T object) {
		return null == toStringFunction ? "" : toStringFunction.apply(object);
	}

	@Override
	public T fromString(String string) {
		return null == fromStringFunction ? null : fromStringFunction.apply(string);
	}

}
