package com.stox.module.watchlist.event;

import com.stox.module.watchlist.model.WatchlistEntry;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

@Getter
public class WatchlistEntryCreatedEvent extends Event{
	private static final long serialVersionUID = 1L;
	
	public static final EventType<WatchlistEntryCreatedEvent> TYPE = new EventType<>("WatchlistEntryCreatedEvent");
	
	private final WatchlistEntry watchlistEntry;
	
	public WatchlistEntryCreatedEvent(final WatchlistEntry watchlistEntry) {
		super(TYPE);
		this.watchlistEntry = watchlistEntry;
	}

}
