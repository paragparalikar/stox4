package com.stox.watchlist;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.scrip.ScripService;

import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class WatchlistView extends BorderPane {

	private final ScripService scripService;
	private final WatchlistService watchlistService;
	
	private final WatchlistControlsMenuButton watchlistButtonBar;
	private final WatchlistComboBox watchlistComboBox;
	private final WatchlistTitleBar watchlistTitleBar;
	private final WatchlistEntryView watchlistEntryView;
	
	public WatchlistView(EventBus eventBus, WatchlistService watchlistService, ScripService scripService) {
		this.scripService = scripService;
		this.watchlistService = watchlistService;

		this.watchlistComboBox = new WatchlistComboBox(eventBus, watchlistService);
		this.watchlistButtonBar = new WatchlistControlsMenuButton(watchlistComboBox, watchlistService);
		this.watchlistTitleBar = new WatchlistTitleBar(watchlistComboBox, watchlistButtonBar);
		this.watchlistEntryView = new WatchlistEntryView(eventBus, scripService, watchlistService);
		
		setTop(watchlistTitleBar);
		setCenter(watchlistEntryView);
	}
	
	public void init() {
		watchlistComboBox.init();
	}
}