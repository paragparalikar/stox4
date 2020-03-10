package com.stox.fx.widget.search;

import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public class TopScroller<T> implements Scroller<T> {

	@Override
	public void scroll(ListView<T> listView) {
		listView.scrollTo(0);
	}

	@Override
	public void scroll(TableView<T> tableView) {
		tableView.scrollTo(0);
	}

}
