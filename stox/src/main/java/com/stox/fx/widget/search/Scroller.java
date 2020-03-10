package com.stox.fx.widget.search;

import java.util.Optional;

import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public interface Scroller<T> {
	
	public static <T> Scroller<T> of(final T item) {
		return Optional.ofNullable(item).<Scroller<T>>map(ItemScroller::new).orElseGet(TopScroller::new);
	}
	
	void scroll(ListView<T> listView);
	
	void scroll(TableView<T> tableView);
	
}
