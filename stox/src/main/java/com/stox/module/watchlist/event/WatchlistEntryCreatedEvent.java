package com.stox.module.watchlist.event;

import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.model.WatchlistEntry;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class WatchlistEntryCreatedEvent extends Event{
	private static final long serialVersionUID = 1L;
	
	public static final EventType<WatchlistEntryCreatedEvent> TYPE = new EventType<>("WatchlistEntryCreatedEvent");
	
	private final Watchlist watchlist;
	private final WatchlistEntry watchlistEntry;
	
	public WatchlistEntryCreatedEvent(
			@NonNull final Watchlist watchlist, 
			@NonNull final WatchlistEntry watchlistEntry) {
		super(TYPE);
		this.watchlist = watchlist;
		this.watchlistEntry = watchlistEntry;
	}

}
