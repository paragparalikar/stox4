package com.stox.watchlist;

import java.util.List;

import org.greenrobot.eventbus.EventBus;

import com.stox.watchlist.event.WatchlistClearedEvent;
import com.stox.watchlist.event.WatchlistCreatedEvent;
import com.stox.watchlist.event.WatchlistDeletedEvent;
import com.stox.watchlist.event.WatchlistEntryAddedEvent;
import com.stox.watchlist.event.WatchlistEntryRemovedEvent;
import com.stox.watchlist.event.WatchlistRenamedEvent;
import com.stox.watchlist.event.WatchlistUpdatedEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WatchlistService {

	private final EventBus eventBus;
	private final WatchlistRepository watchlistRepository;
	
	public List<Watchlist> findAll(){
		return watchlistRepository.findAll();
	}
	
	public void create(Watchlist watchlist) {
		watchlistRepository.create(watchlist);
		eventBus.post(new WatchlistCreatedEvent(watchlist));
	}
	
	public void update(Watchlist watchlist) {
		watchlistRepository.update(watchlist);
		eventBus.post(new WatchlistUpdatedEvent(watchlist));
	}
	
	public void clear(String name) {
		final Watchlist watchlist = watchlistRepository.truncate(name);
		eventBus.post(new WatchlistClearedEvent(watchlist));
	}
	
	public void rename(String oldName, String newName) {
		watchlistRepository.rename(oldName, newName);
		eventBus.post(new WatchlistRenamedEvent(oldName, newName));
	}
	
	public void delete(String name) {
		final Watchlist watchlist = watchlistRepository.delete(name);
		eventBus.post(new WatchlistDeletedEvent(watchlist));
	}
	
	public void addEntry(String name, String entry) {
		final Watchlist watchlist = watchlistRepository.findByName(name);
		if(!watchlist.getEntries().contains(entry)) {
			watchlist.getEntries().add(entry);
			watchlistRepository.update(watchlist);
			eventBus.post(new WatchlistEntryAddedEvent(name, entry));
		}
	}
	
	public void removeEntry(String name, String entry) {
		final Watchlist watchlist = watchlistRepository.findByName(name);
		if(watchlist.getEntries().contains(entry)) {
			watchlist.getEntries().remove(entry);
			watchlistRepository.update(watchlist);
			eventBus.post(new WatchlistEntryRemovedEvent(name, entry));
		}
	}
}
