package com.stox.fx.widget.search;

import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemScroller<T> implements Scroller<T> {

	private final T item;
	
	@Override
	public void scroll(@NonNull final ListView<T> listView) {
		listView.scrollTo(item);
	}

	@Override
	public void scroll(@NonNull final TableView<T> tableView) {
		tableView.scrollTo(item);
	}

}
