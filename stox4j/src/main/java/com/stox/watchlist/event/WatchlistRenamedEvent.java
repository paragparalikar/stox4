package com.stox.watchlist.event;

import lombok.Value;

@Value
public class WatchlistRenamedEvent {

	private final String oldName;
	private final String newName;
	
}
