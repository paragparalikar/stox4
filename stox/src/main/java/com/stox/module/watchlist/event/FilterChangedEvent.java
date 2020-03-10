package com.stox.module.watchlist.event;

import java.util.function.Predicate;

import com.stox.module.watchlist.model.WatchlistEntry;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class FilterChangedEvent extends Event{
	private static final long serialVersionUID = 8844773417548900480L;

	public static final EventType<FilterChangedEvent> TYPE = new EventType<>("FilterChangedEvent"); 
	
	private final Predicate<WatchlistEntry> predicate;
	
	public FilterChangedEvent(@NonNull final Predicate<WatchlistEntry> predicate) {
		super(TYPE);
		this.predicate = predicate;
	}	

}
