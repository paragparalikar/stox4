package com.stox.watchlist;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.SerializationService;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;
import com.stox.common.ui.View;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class WatchlistTab extends Tab implements View {
	
	private final WatchlistView watchlistView;

	public WatchlistTab(EventBus eventBus, ScripService scripService, 
			WatchlistService watchlistService, SerializationService serializationService) {
		super("Watchlists");
		final Label graphics = new Label(Icon.BOOKMARK);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		watchlistView = new WatchlistView(eventBus, watchlistService, scripService, serializationService);
		setContent(watchlistView);
	}

	@Override public void load() { watchlistView.load(); }
	@Override public void unload() { watchlistView.unload(); }
	
}
