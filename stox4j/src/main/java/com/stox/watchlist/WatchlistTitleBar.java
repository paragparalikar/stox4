package com.stox.watchlist;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WatchlistTitleBar extends VBox {

	private final WatchlistComboBox watchlistComboBox;
	private final WatchlistButtonBar watchlistButtonBar;
	private final HBox container = new HBox();
	
	public WatchlistTitleBar(WatchlistComboBox watchlistComboBox, 
			WatchlistButtonBar watchlistButtonBar) {
		this.watchlistComboBox = watchlistComboBox;
		this.watchlistButtonBar = watchlistButtonBar;
		container.getChildren().addAll(watchlistComboBox, watchlistButtonBar);
		getChildren().add(container);
	}

}
