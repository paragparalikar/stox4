package com.stox.module.watchlist.event;

import com.stox.module.watchlist.model.WatchlistEntry;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class WatchlistEntryDeletedEvent extends Event {
	private static final long serialVersionUID = 1L;

	public static final EventType<WatchlistEntryDeletedEvent> TYPE = new EventType<>("WatchlistEntryDeletedEvent");

	private final WatchlistEntry watchlistEntry;
	
	public WatchlistEntryDeletedEvent(@NonNull final WatchlistEntry watchlistEntry) {
		super(TYPE);
		this.watchlistEntry = watchlistEntry;
	}

}
