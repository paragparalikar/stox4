package com.stox.fx.fluent.beans.binding;

import java.util.function.Supplier;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;

public class FluentStringBinding extends StringBinding{

	private final Supplier<String> supplier;
	
	public FluentStringBinding(final Supplier<String> supplier, final ObservableValue<?>...dependencies) {
		this.supplier = supplier;
		bind(dependencies);
	}
	
	@Override
	protected String computeValue() {
		return supplier.get();
	}

}
