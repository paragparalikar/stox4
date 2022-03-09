package com.stox.watchlist;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class WatchlistTab extends Tab {

	private final EventBus eventBus;
	private final ScripService scripService;
	private final WatchlistService watchlistService;
	
	public WatchlistTab(EventBus eventBus, ScripService scripService, WatchlistService watchlistService) {
		super("Watchlists");
		this.eventBus = eventBus;
		this.scripService = scripService;
		this.watchlistService = watchlistService;
		final Label graphics = new Label(Icon.BOOKMARK);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		setOnSelectionChanged(event -> init());
	}
	
	private void init() {
		if(isSelected() && null == getContent()) {
			final WatchlistView watchlistView = new WatchlistView(eventBus, watchlistService, scripService);
			setContent(watchlistView);
			watchlistView.init();
		}
	}
	
}
