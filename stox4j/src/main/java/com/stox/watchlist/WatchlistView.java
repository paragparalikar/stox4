package com.stox.watchlist;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.scrip.ScripService;
import com.stox.watchlist.menu.WatchlistContextMenu;
import com.stox.watchlist.menu.WatchlistControlsMenuButton;

import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class WatchlistView extends BorderPane {

	private final ScripService scripService;
	private final WatchlistService watchlistService;
	private final WatchlistComboBox watchlistComboBox;
	
	public WatchlistView(EventBus eventBus, WatchlistService watchlistService, ScripService scripService) {
		this.scripService = scripService;
		this.watchlistService = watchlistService;

		this.watchlistComboBox = new WatchlistComboBox(eventBus, watchlistService);
		final WatchlistControlsMenuButton watchlistButtonBar = new WatchlistControlsMenuButton(watchlistComboBox, watchlistService);
		final WatchlistTitleBar watchlistTitleBar = new WatchlistTitleBar(watchlistComboBox, watchlistButtonBar);
		final WatchlistEntryView watchlistEntryView = new WatchlistEntryView(eventBus, scripService, watchlistComboBox);
		final WatchlistEntryCellFactory watchlistEntryCellFactory = new WatchlistEntryCellFactory(scripService, watchlistService, watchlistComboBox);
		final WatchlistContextMenu watchlistContextMenu = new WatchlistContextMenu(watchlistEntryView, watchlistService, watchlistComboBox);
		
		watchlistEntryView.setContextMenu(watchlistContextMenu);
		watchlistEntryView.setCellFactory(watchlistEntryCellFactory);
		
		setTop(watchlistTitleBar);
		setCenter(watchlistEntryView);
	}
	
	public void init() {
		watchlistComboBox.init();
	}
}