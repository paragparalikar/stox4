package com.stox.watchlist;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WatchlistService {

	private final WatchlistRepository watchlistRepository;
	
	public CompletableFuture<List<Watchlist>> findAll(){
		return watchlistRepository.findAll();
	}
	
	public CompletableFuture<Watchlist> create(Watchlist watchlist) {
		return watchlistRepository.create(watchlist).thenApplyAsync(result -> {
			return watchlist;
		});
	}
	
	public CompletableFuture<Watchlist> delete(String name) {
		return watchlistRepository.delete(name).thenApplyAsync(result -> {
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
}
