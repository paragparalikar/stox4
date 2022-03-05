package com.stox.watchlist;

import com.stox.charting.ChartingView;
import com.stox.common.scrip.ScripService;

import javafx.scene.layout.BorderPane;

public class WatchlistView extends BorderPane {

	private final ChartingView chartingView;
	private final ScripService scripService;
	private final WatchlistService watchlistService;
	
	private final WatchlistButtonBar watchlistButtonBar;
	private final WatchlistComboBox watchlistComboBox;
	private final WatchlistTitleBar watchlistTitleBar;
	private final WatchlistEntryView watchlistEntryView;
	
	public WatchlistView(WatchlistService watchlistService, 
			ScripService scripService, ChartingView chartingView) {
		this.chartingView = chartingView;
		this.scripService = scripService;
		this.watchlistService = watchlistService;

		this.watchlistComboBox = new WatchlistComboBox(watchlistService);
		this.watchlistButtonBar = new WatchlistButtonBar(watchlistComboBox, watchlistService);
		this.watchlistTitleBar = new WatchlistTitleBar(watchlistComboBox, watchlistButtonBar);
		this.watchlistEntryView = new WatchlistEntryView(scripService, watchlistService);
		
		setTop(watchlistTitleBar);
		setCenter(watchlistEntryView);
	}
}