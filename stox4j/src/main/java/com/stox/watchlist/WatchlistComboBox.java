package com.stox.watchlist;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.watchlist.event.WatchlistClearedEvent;
import com.stox.watchlist.event.WatchlistCreatedEvent;
import com.stox.watchlist.event.WatchlistDeletedEvent;
import com.stox.watchlist.event.WatchlistEntryAddedEvent;
import com.stox.watchlist.event.WatchlistEntryRemovedEvent;
import com.stox.watchlist.event.WatchlistRenamedEvent;
import com.stox.watchlist.event.WatchlistUpdatedEvent;

import javafx.scene.control.ComboBox;

public class WatchlistComboBox extends ComboBox<Watchlist> {
	
	public WatchlistComboBox(EventBus eventBus) {
		eventBus.register(this);
	}
	
	private Optional<Watchlist> findItem(String name) {
		return getItems().stream()
				.filter(item -> item.getName().equalsIgnoreCase(name))
				.findAny();
	}
	
	@Subscribe
	public void onWatchlistCreated(WatchlistCreatedEvent event) {
		getItems().add(event.getWatchlist());
		getSelectionModel().select(event.getWatchlist());
	}
	
	@Subscribe
	public void onWatchlistRenamed(WatchlistRenamedEvent event) {
		findItem(event.getOldName()).ifPresent(watchlist -> {
			watchlist.setName(event.getNewName());
			final List<Watchlist> watchlists = getItems().stream()
					.sorted(Comparator.comparing(Watchlist::getName))
					.collect(Collectors.toList());
			getItems().setAll(watchlists);
			if(Objects.equals(getValue(), watchlist) && null != getButtonCell()) {
				getButtonCell().setText(watchlist.getName());
			}
		});
	}
	
	@Subscribe
	public void onWatchlistUpdated(WatchlistUpdatedEvent event) {
		findItem(event.getWatchlist().getName()).ifPresent(watchlist -> {
			if(watchlist != event.getWatchlist()) {
				watchlist.getEntries().clear();
				watchlist.getEntries().addAll(event.getWatchlist().getEntries());
			}
		});
	}
	
	@Subscribe
	public void onWatchlistCleared(WatchlistClearedEvent event) {
		findItem(event.getWatchlist().getName()).ifPresent(watchlist -> {
			watchlist.getEntries().clear();
		});
	}
	
	@Subscribe
	public void onWatchlistDeletetd(WatchlistDeletedEvent event) {
		findItem(event.getWatchlist().getName()).ifPresent(getItems()::remove);
	}

	@Subscribe
	public void onWatchlistEntryAdded(WatchlistEntryAddedEvent event) {
		findItem(event.getName()).ifPresent(watchlist -> {
			if(!watchlist.getEntries().contains(event.getEntry())) {
				watchlist.getEntries().add(event.getEntry());
			}
		});
	}

	@Subscribe
	public void onWatchlistEntryRemoved(WatchlistEntryRemovedEvent event) {
		findItem(event.getName()).ifPresent(watchlist -> {
			watchlist.getEntries().remove(event.getEntry());
		});
	}
}
