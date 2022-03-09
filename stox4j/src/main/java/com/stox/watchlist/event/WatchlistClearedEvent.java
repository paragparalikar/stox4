package com.stox.watchlist.event;

import com.stox.watchlist.Watchlist;

import lombok.Value;

@Value
public class WatchlistClearedEvent {

	private final Watchlist watchlist;
	
}
