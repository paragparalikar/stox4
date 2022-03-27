package com.stox.watchlist;

import com.stox.watchlist.menu.WatchlistControlsMenuButton;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class WatchlistTitleBar extends VBox {
	
	public WatchlistTitleBar(WatchlistComboBox watchlistComboBox, WatchlistControlsMenuButton watchlistControlsMenuButton) {
		HBox.setHgrow(watchlistComboBox, Priority.ALWAYS);
		watchlistComboBox.setMaxWidth(Double.MAX_VALUE);
		getChildren().addAll(new HBox(watchlistComboBox, watchlistControlsMenuButton));
	}

}
