package com.stox.fx.widget.search;

import com.stox.fx.fluent.scene.control.FluentListView;

public class SearchableListView<T> extends FluentListView<T> implements Searchable<T> {

	@Override
	public int size() {
		return getItems().size();
	}

	@Override
	public int getSelectedIndex() {
		return getSelectionModel().getSelectedIndex();
	}

	@Override
	public void select(int index) {
		getSelectionModel().select(index);
	}

	@Override
	public T get(int index) {
		return getItems().get(index);
	}

}
