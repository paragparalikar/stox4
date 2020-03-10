package com.stox.fx.widget.search;

import java.util.Optional;

import javafx.scene.control.SelectionModel;

public interface Selector<T>{
	
	public static <T> Selector<T> of(final T item){
		return Optional.ofNullable(item).<Selector<T>>map(ItemSelector::new).orElseGet(NoneSelector::new);
	}
	
	void select(SelectionModel<T> selectionModel);
	
}