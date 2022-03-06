package com.stox.watchlist;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WatchlistService {

	private final WatchlistRepository watchlistRepository;
	private final List<Watchlist> watchlists = new LinkedList<>();
	
	public CompletableFuture<List<Watchlist>> findAll(){
		return watchlists.isEmpty() ? watchlistRepository.findAll().thenApply(this::cache) 
				: CompletableFuture.completedFuture(watchlists);
	}
	
	private List<Watchlist> cache(List<Watchlist> watchlists){
		this.watchlists.addAll(watchlists);
		return  watchlists;
	}
	
	public CompletableFuture<Watchlist> create(Watchlist watchlist) {
		return watchlistRepository.create(watchlist).thenApplyAsync(result -> {
			watchlists.add(watchlist);
			return watchlist;
		});
	}
	
	public CompletableFuture<Watchlist> delete(String name) {
		return watchlistRepository.delete(name).thenApplyAsync(result -> {
			watchlists.remove(result);
			return result;
		});
	}
	
	public CompletableFuture<Boolean> exists(String name){
		return findAll().thenApply(watchlists -> {
			return watchlists.stream().map(Watchlist::getName)
					.anyMatch(watchlistName -> watchlistName.equalsIgnoreCase(name));
		});
	}
	
	public CompletableFuture<Void> addEntry(String name, String isin) {
		return watchlistRepository.addEntry(name, isin);
	}
	
	public CompletableFuture<Void> removeEntry(String name, int index) {
		return watchlistRepository.removeEntry(name, index);
	}
	
	public CompletableFuture<Void> setEntryIndex(String name, String isin, int index) {
		return watchlistRepository.setEntryIndex(name, isin, index);
	}
}
