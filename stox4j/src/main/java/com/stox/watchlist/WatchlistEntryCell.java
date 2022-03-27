package com.stox.watchlist;

import java.util.Optional;

import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import lombok.Builder;

@Builder
public class WatchlistEntryCell extends ListCell<String> {
	
	private final ScripService scripService;
	private final WatchlistService watchlistService;
	private final ComboBox<Watchlist> watchlistComboBox;
	private final Button deleteButton = new Button(Icon.TRASH);
	
	public WatchlistEntryCell(
			ScripService scripService, 
			WatchlistService watchlistService,
			ComboBox<Watchlist> watchlistComboBox) {
		this.scripService = scripService;
		this.watchlistService = watchlistService;
		this.watchlistComboBox = watchlistComboBox;
		deleteButton.getStyleClass().setAll("icon", "button");
	}
	
	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if(empty || null == item || 0 == item.trim().length()) {
			setText(null);
			setGraphic(null);
		} else {
			final Scrip scrip = scripService.findByIsin(item);
			setText(null == scrip ? item : scrip.getName());
			setGraphic(deleteButton);
			deleteButton.setOnAction(event -> remove(item));
		}
	}
	
	private void remove(String isin) {
		Optional.ofNullable(watchlistComboBox.getValue()).ifPresent(watchlist -> {
			watchlistService.removeEntry(watchlist.getName(), isin);
		});
	}
}
