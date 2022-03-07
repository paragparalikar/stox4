package com.stox.watchlist;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WatchlistService {

	private final WatchlistFileRepository watchlistRepository;
	private final ObservableMap<String, Watchlist> cache = FXCollections.observableHashMap();
	
	public void onWatchlistAdded(Consumer<Watchlist> callback) {
		cache.addListener((MapChangeListener<String, Watchlist>)change -> {
			if(change.wasAdded()) callback.accept(change.getValueAdded());
		});
	}
	
	public void onWatchlistRemoved(Consumer<Watchlist> callback) {
		cache.addListener((MapChangeListener<String, Watchlist>)change -> {
			if(change.wasRemoved()) callback.accept(change.getValueRemoved());
		});
	}
	
	public synchronized List<Watchlist> findAll(){
		if(cache.isEmpty()) {
			final List<Watchlist> watchlists = watchlistRepository.findAll();
			watchlists.forEach(watchlist -> cache.put(watchlist.getName(), watchlist));
			return watchlists;
		} else {
			return new ArrayList<>(cache.values());
		}
	}
	
	public synchronized void save(Watchlist watchlist) {
		watchlistRepository.save(watchlist);
		cache.put(watchlist.getName(), watchlist);
	}
	
	public synchronized void clear(String name) {
		watchlistRepository.truncate(name);
		//cache.get(name).getEntries().clear();
	}
	
	public synchronized void rename(String oldName, String newName) {
		watchlistRepository.rename(oldName, newName);
		final Watchlist watchlist = cache.remove(oldName);
		if(null != watchlist) {
			watchlist.setName(newName);
			cache.put(newName, watchlist);
		}
	}
	
	public synchronized void delete(String name) {
		watchlistRepository.delete(name);
		cache.remove(name);
	}
	
	public synchronized void addEntry(String name, String entry) {
		watchlistRepository.append(name, entry);
		//cache.get(name).getEntries().add(entry);
	}
	
	public synchronized void removeEntry(String name, String entry) {
		final Watchlist watchlist = watchlistRepository.findByName(name);
		watchlist.getEntries().remove(entry);
		save(watchlist);
	}
}
