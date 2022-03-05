package com.stox.watchlist;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class WatchlistComboBox extends ComboBox<Watchlist> {

	public WatchlistComboBox(WatchlistService watchlistService) {
		setCellFactory(this::createWatchlistCell);
		watchlistService.findAll().whenComplete(this::onWatchlistsLoaded);
	}
	
	private void onWatchlistsLoaded(List<Watchlist> watchlists, Throwable throwable) {
		Optional.ofNullable(throwable).ifPresent(Throwable::printStackTrace);
		Optional.ofNullable(watchlists).ifPresent(w -> {
			watchlists.sort(Comparator.comparing(Watchlist::getName));
			Platform.runLater(() -> getItems().setAll(watchlists));
		});
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
