package com.stox.module.watchlist.event;

import com.stox.module.watchlist.model.Watchlist;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class WatchlistClearedEvent extends Event {
	private static final long serialVersionUID = -3311657119005198068L;

	public static final EventType<WatchlistClearedEvent> TYPE = new EventType<>("WatchlistClearedEvent");
	
	private final Watchlist watchlist;
	
	public WatchlistClearedEvent(@NonNull final Watchlist watchlist) {
		super(TYPE);
		this.watchlist = watchlist;
	}

}
