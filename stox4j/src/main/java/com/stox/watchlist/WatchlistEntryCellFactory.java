package com.stox.watchlist;

import com.stox.common.scrip.ScripService;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WatchlistEntryCellFactory implements Callback<ListView<String>, ListCell<String>> {

	private final ScripService scripService;
	private final WatchlistService watchlistService;
	private final ComboBox<Watchlist> watchlistComboBox;
	
	@Override
	public ListCell<String> call(ListView<String> param) {
		return WatchlistEntryCell.builder()
				.scripService(scripService)
				.watchlistService(watchlistService)
				.watchlistComboBox(watchlistComboBox)
				.build();
	}

}
