package com.stox.module.screen;

import com.stox.fx.widget.search.SearchableListView;

public class ScreenListView extends SearchableListView<Screen<?>> {

	public ScreenListView() {
		getItems().addAll(Screen.ALL);
		setCellFactory(listView -> new ScreenListCell());
	}

}
