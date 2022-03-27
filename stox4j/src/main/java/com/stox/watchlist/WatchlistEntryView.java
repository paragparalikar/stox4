package com.stox.watchlist;

import java.util.Collections;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.common.event.ScripSelectedEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.watchlist.event.WatchlistClearedEvent;
import com.stox.watchlist.event.WatchlistEntryAddedEvent;
import com.stox.watchlist.event.WatchlistEntryRemovedEvent;
import com.stox.watchlist.event.WatchlistUpdatedEvent;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class WatchlistEntryView extends ListView<String> {

	private final EventBus eventBus;
	private final ScripService scripService;
	private final ComboBox<Watchlist> watchlistComboBox;
	
	public WatchlistEntryView(EventBus eventBus, ScripService scripService, ComboBox<Watchlist> watchlistComboBox) {
		this.eventBus = eventBus;
		this.scripService = scripService;
		this.watchlistComboBox = watchlistComboBox;
		getSelectionModel().selectedItemProperty().addListener(this::onWatchlistEntrySelected);
		watchlistComboBox.getSelectionModel().selectedItemProperty().addListener(this::onWatchlistSelected);
		eventBus.register(this);
	}
	
	private void onWatchlistEntrySelected(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		final Scrip scrip = scripService.findByIsin(newValue);
		if(null != scrip) eventBus.post(new ScripSelectedEvent(scrip));
	}
	
	private void onWatchlistSelected(ObservableValue<? extends Watchlist> observable, Watchlist old, Watchlist watchlist) {
		getItems().clear();
		getItems().setAll(Optional.ofNullable(watchlist)
				.map(Watchlist::getEntries)
				.orElse(Collections.emptyList()));
	}
	
	@Subscribe
	public void onWatchlistUpdated(WatchlistUpdatedEvent event) {
		final Watchlist watchlist = watchlistComboBox.getValue();
		if(null != watchlist && watchlist.getName().equalsIgnoreCase(event.getWatchlist().getName())) {
			if(watchlist != event.getWatchlist()) {
				watchlist.getEntries().clear();
				watchlist.getEntries().addAll(event.getWatchlist().getEntries());
			}
			getItems().setAll(event.getWatchlist().getEntries());
		}
	}
	
	@Subscribe
	public void onWatchlistCleared(WatchlistClearedEvent event) {
		final Watchlist watchlist = watchlistComboBox.getValue();
		if(null != watchlist && watchlist.getName().equalsIgnoreCase(event.getWatchlist().getName())) {
			if(watchlist != event.getWatchlist()) {
				watchlist.getEntries().clear();
			}
			getItems().clear();
		}
	}
	
	@Subscribe
	public void onWatchlistEntryAdded(WatchlistEntryAddedEvent event) {
		final Watchlist watchlist = watchlistComboBox.getValue();
		if(null != watchlist && watchlist.getName().equalsIgnoreCase(event.getName())) {
			if(!watchlist.getEntries().contains(event.getEntry())) {
				watchlist.getEntries().add(event.getEntry());
			}
			if(!getItems().contains(event.getEntry())) {
				getItems().add(event.getEntry());
			}
		}
	}

	@Subscribe
	public void onWatchlistEntryRemoved(WatchlistEntryRemovedEvent event) {
		final Watchlist watchlist = watchlistComboBox.getValue();
		if(null != watchlist && watchlist.getName().equalsIgnoreCase(event.getName())) {
			watchlist.getEntries().remove(event.getEntry());
			getItems().remove(event.getEntry());
		}
	}
	
}
