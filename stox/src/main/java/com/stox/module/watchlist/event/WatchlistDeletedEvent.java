package com.stox.module.watchlist.event;

import com.stox.module.watchlist.model.Watchlist;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class WatchlistDeletedEvent extends Event {
	private static final long serialVersionUID = 7929042272278110632L;

	public static final EventType<WatchlistDeletedEvent> TYPE = new EventType<>("WatchlistDeletedEvent");
	
	private final Watchlist watchlist;
	
	public WatchlistDeletedEvent(@NonNull final Watchlist watchlist) {
		super(TYPE);
		this.watchlist = watchlist;
	}


}