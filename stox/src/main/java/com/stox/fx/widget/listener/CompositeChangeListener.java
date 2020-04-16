package com.stox.fx.widget.listener;

import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class CompositeChangeListener<T> implements ChangeListener<T> {
	
	private final Set<ChangeListener<T>> children = Collections.newSetFromMap(new IdentityHashMap<>());
	
	public CompositeChangeListener() {
	}
	
	@SafeVarargs
	public CompositeChangeListener(@NonNull ChangeListener<T>...changeListeners) {
		children.addAll(Arrays.asList(changeListeners));
	}
	
	public CompositeChangeListener<T> add(@NonNull final ChangeListener<T> changeListener){
		children.add(changeListener);
		return this;
	}

	@Override
	public void changed(final ObservableValue<? extends T> observableValue, final T old, final T value) {
		children.forEach(child -> child.changed(observableValue, old, value));
	}

}
