package com.stox.watchlist;

import java.util.Collections;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.common.event.ScripSelectedEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;
import com.stox.watchlist.event.WatchlistClearedEvent;
import com.stox.watchlist.event.WatchlistEntryAddedEvent;
import com.stox.watchlist.event.WatchlistEntryRemovedEvent;
import com.stox.watchlist.event.WatchlistSelectedEvent;
import com.stox.watchlist.event.WatchlistUpdatedEvent;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class WatchlistEntryView extends ListView<String> {

	private Watchlist watchlist;
	private final EventBus eventBus;
	private final ScripService scripService;
	private final WatchlistService watchlistService;
	
	public WatchlistEntryView(EventBus eventBus, ScripService scripService, WatchlistService watchlistService) {
		this.eventBus = eventBus;
		this.scripService = scripService;
		this.watchlistService = watchlistService;
		setCellFactory(this::createWatchlistEntryCell);
		getSelectionModel().selectedItemProperty().addListener(this::changed);
		eventBus.register(this);
	}
	
	private void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		final Scrip scrip = scripService.findByIsin(newValue);
		if(null != scrip) eventBus.post(new ScripSelectedEvent(scrip));
	}
	
	@Subscribe
	public void onWatchlistSelected(WatchlistSelectedEvent event) {
		this.watchlist = event.getWatchlist();
		getItems().setAll(Optional.ofNullable(event.getWatchlist())
				.map(Watchlist::getEntries)
				.orElse(Collections.emptyList()));
	}
	
	@Subscribe
	public void onWatchlistUpdated(WatchlistUpdatedEvent event) {
		if(null != watchlist && watchlist.getName().equalsIgnoreCase(event.getWatchlist().getName())) {
			watchlist.getEntries().clear();
			watchlist.getEntries().addAll(event.getWatchlist().getEntries());
			getItems().setAll(watchlist.getEntries());
		}
	}
	
	@Subscribe
	public void onWatchlistCleared(WatchlistClearedEvent event) {
		if(null != watchlist && watchlist.getName().equalsIgnoreCase(event.getWatchlist().getName())) {
			watchlist.getEntries().clear();
			getItems().clear();
		}
	}
	
	@Subscribe
	public void onWatchlistEntryAdded(WatchlistEntryAddedEvent event) {
		if(null != watchlist && watchlist.getName().equalsIgnoreCase(event.getName())) {
			if(!watchlist.getEntries().contains(event.getEntry())) {
				watchlist.getEntries().add(event.getEntry());
				getItems().add(event.getEntry());
			}
		}
	}

	@Subscribe
	public void onWatchlistEntryRemoved(WatchlistEntryRemovedEvent event) {
		if(null != watchlist && watchlist.getName().equalsIgnoreCase(event.getName())) {
			if(watchlist.getEntries().contains(event.getEntry())) {
				watchlist.getEntries().remove(event.getEntry());
				getItems().remove(event.getEntry());
			}
		}
	}
	
	private ListCell<String> createWatchlistEntryCell(ListView<String> listView){
		return new ListCell<String>() {
			private final Button deleteButton = new Button(Icon.TRASH);
			{deleteButton.getStyleClass().setAll("icon", "button");}
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
		};
	}

	private void remove(String isin) {
		watchlistService.removeEntry(watchlist.getName(), isin);
	}
}
