package com.stox.watchlist.event;

import lombok.Value;

@Value
public class WatchlistEntryAddedEvent {

	private String name;
	private String entry;
	
}
