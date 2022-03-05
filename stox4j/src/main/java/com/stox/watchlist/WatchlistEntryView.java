package com.stox.watchlist;

import java.util.Optional;

import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class WatchlistEntryView extends ListView<String> implements ChangeListener<Watchlist> {

	private volatile Watchlist watchlist;
	private final ScripService scripService;
	private final WatchlistService watchlistService;
	
	public WatchlistEntryView(ScripService scripService, WatchlistService watchlistService) {
		this.scripService = scripService;
		this.watchlistService = watchlistService;
		setCellFactory(this::createWatchlistEntryCell);
	}
	
	@Override
	public void changed(ObservableValue<? extends Watchlist> observable, Watchlist oldValue, Watchlist newValue) {
		itemsProperty().unbind();
		Optional.ofNullable(newValue)
			.map(watchlist -> this.watchlist = watchlist)
			.map(Watchlist::entriesProperty)
			.ifPresent(itemsProperty()::bind);
	}
	
	private ListCell<String> createWatchlistEntryCell(ListView<String> listView){
		return new ListCell<String>() {
			private final Button deleteButton = new Button(Icon.TRASH);
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(empty || null != watchlist || null == item || 0 == item.trim().length()) {
					setText(null);
					setGraphic(null);
				} else {
					final Scrip scrip = scripService.findByIsin(item);
					setText(null == scrip ? item : scrip.getName());
					setGraphic(deleteButton);
					deleteButton.getStyleClass().setAll("icon", "button");
					deleteButton.setOnAction(event -> {
						final int index = watchlist.getEntries().indexOf(item);
						watchlistService.removeEntry(watchlist.getName(), index);
					});
				}
			}
		};
	}

}
