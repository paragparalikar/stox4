package com.stox.fx.widget.search;

import javafx.scene.control.SelectionModel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ItemSelector<T> extends NoneSelector<T>{

	private final T item;
	
	@Override
	public void select(@NonNull final SelectionModel<T> selectionModel) {
		super.select(selectionModel);
		selectionModel.select(item);
	}
	
}