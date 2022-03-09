package com.stox.watchlist;

import java.util.List;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.common.event.SelectedScripQueryEvent;
import com.stox.common.scrip.Scrip;
import com.stox.watchlist.event.WatchlistClearedEvent;
import com.stox.watchlist.event.WatchlistCreatedEvent;
import com.stox.watchlist.event.WatchlistDeletedEvent;
import com.stox.watchlist.event.WatchlistEntryAddedEvent;
import com.stox.watchlist.event.WatchlistEntryRemovedEvent;
import com.stox.watchlist.event.WatchlistRenamedEvent;
import com.stox.watchlist.event.WatchlistUpdatedEvent;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class AddToWatchlistMenu extends Menu {

	private final EventBus eventBus;
	private final WatchlistService watchlistService;
	
	public AddToWatchlistMenu(EventBus eventBus, WatchlistService watchlistService) {
		super("Add to watchlist");
		this.eventBus = eventBus;
		this.watchlistService = watchlistService;
	}
	
	public void init() {
		watchlistService.findAll().forEach(this::createMenuItem);
		eventBus.register(this);
	}
	
	private Optional<MenuItem> findItem(String name){
		return getItems().stream()
				.filter(item -> item.getText().equalsIgnoreCase(name))
				.findAny();
	}

	@Subscribe
	public void onWatchlistCreated(WatchlistCreatedEvent event) {
		createMenuItem(event.getWatchlist());
	}
	
	@Subscribe
	public void onWatchlistCleared(WatchlistClearedEvent event) {
		findItem(event.getWatchlist().getName()).ifPresent(item -> {
			Watchlist.class.cast(item.getUserData()).getEntries().clear();
		});
	}
	
	@Subscribe
	public void onWatchlistUpdated(WatchlistUpdatedEvent event) {
		findItem(event.getWatchlist().getName()).ifPresent(item -> {
			final List<String> entries = Watchlist.class.cast(item.getUserData()).getEntries();
			entries.clear();
			entries.addAll(event.getWatchlist().getEntries());
		});
	}
	
	@Subscribe
	public void onWatchlistRenamed(WatchlistRenamedEvent event) {
		findItem(event.getOldName()).ifPresent(item -> {
			item.setText(event.getNewName());
			Watchlist.class.cast(item.getUserData()).setName(event.getNewName());
		});
	}
	
	@Subscribe
	public void onWatchlistDeleted(WatchlistDeletedEvent event) {
		findItem(event.getWatchlist().getName()).ifPresent(item -> {
			getItems().remove(item);
		});
	}
	
	@Subscribe
	public void onWatchlistEntryAdded(WatchlistEntryAddedEvent event) {
		findItem(event.getName()).ifPresent(item -> {
			Watchlist.class.cast(item.getUserData()).getEntries().add(event.getEntry());
		});
	}
	
	@Subscribe
	public void onWatchlistEntryRemoved(WatchlistEntryRemovedEvent event) {
		findItem(event.getName()).ifPresent(item -> {
			Watchlist.class.cast(item.getUserData()).getEntries().remove(event.getEntry());
		});
	}
	
	private void createMenuItem(Watchlist watchlist) {
		final MenuItem menuItem = new MenuItem(watchlist.getName());
		menuItem.setOnAction(event -> add(watchlist));
		menuItem.setUserData(watchlist);
		getItems().add(menuItem);
	}

	private void add(Watchlist watchlist) {
		final Scrip scrip = getSelectedScrip();
		if(null != scrip && !watchlist.getEntries().contains(scrip.getIsin())) {
			watchlistService.addEntry(watchlist.getName(), scrip.getIsin());
			watchlist.getEntries().add(scrip.getIsin());
		}
	}
	
	private Scrip getSelectedScrip() {
		final SelectedScripQueryEvent event = new SelectedScripQueryEvent();
		eventBus.post(event);
		return event.getScrip();
	}
}
