package com.stox.module.watchlist.repository;

import java.util.List;

import com.stox.module.watchlist.model.Watchlist;

public interface WatchlistRepository {
	
	List<Watchlist> findAll();
	
	Watchlist get(String name);
	
	boolean exists(String name);
	
	void delete(String name);
	
	Watchlist save(Watchlist watchlist);
	
	Watchlist clear(Watchlist watchlist);
	
	Watchlist rename(Watchlist watchlist, String newName);

}
