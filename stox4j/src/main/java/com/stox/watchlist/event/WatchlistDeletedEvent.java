package com.stox.watchlist.event;

import com.stox.watchlist.Watchlist;

import lombok.Value;

@Value
public class WatchlistDeletedEvent {

	private final Watchlist watchlist;
	
}
