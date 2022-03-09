package com.stox.watchlist.event;

import lombok.Value;

@Value
public class WatchlistEntryRemovedEvent {

	private String name;
	private String entry;
	
}
