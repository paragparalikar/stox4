package com.stox.watchlist.event;

import com.stox.watchlist.Watchlist;

import lombok.Value;

@Value
public class WatchlistSelectedEvent {

	private final Watchlist watchlist;
	
}