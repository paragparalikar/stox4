package com.stox.fx.widget.search;

import javafx.scene.control.SelectionModel;
import lombok.NonNull;

class NoneSelector<T> implements Selector<T>{

	@Override
	public void select(@NonNull final SelectionModel<T> selectionModel) {
		selectionModel.clearSelection();
	}
	
}