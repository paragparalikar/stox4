package com.stox.module.watchlist.event;

import com.stox.module.watchlist.model.Watchlist;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class WatchlistUpdatedEvent extends Event {
	private static final long serialVersionUID = 7929042272278110632L;

	public static final EventType<WatchlistUpdatedEvent> TYPE = new EventType<>("WatchlistUpdatedEvent");
	
	private final Watchlist watchlist;
	
	public WatchlistUpdatedEvent(@NonNull final Watchlist watchlist) {
		super(TYPE);
		this.watchlist = watchlist;
	}


}
