package com.stox.watchlist;

import java.util.Comparator;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class WatchlistComboBox extends ComboBox<Watchlist> {
	
	private final WatchlistService watchlistService;

	public WatchlistComboBox(WatchlistService watchlistService) {
		this.watchlistService = watchlistService;
		setCellFactory(this::createWatchlistCell);
		setButtonCell(createWatchlistCell(null));
	}
	
	public void init() {
		final List<Watchlist> watchlists = watchlistService.findAll();
		watchlists.sort(Comparator.comparing(Watchlist::getName));
		getItems().setAll(watchlists);
		if(!getItems().isEmpty()) getSelectionModel().select(0);
		watchlistService.onWatchlistAdded(getItems()::add);
		watchlistService.onWatchlistRemoved(getItems()::remove);
	}
	
	private ListCell<Watchlist> createWatchlistCell(ListView<Watchlist> listView){
		return  new ListCell<Watchlist>() {
			@Override
			protected void updateItem(Watchlist item, boolean empty) {
				super.updateItem(item, empty);
				textProperty().unbind();
				setText(null);
				if(null != item && !empty) {
					textProperty().bind(item.nameProperty());
				}
			}
		};
	}

}
