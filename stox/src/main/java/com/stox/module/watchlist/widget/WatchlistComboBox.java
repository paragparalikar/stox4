package com.stox.module.watchlist.widget;

import java.util.Objects;

import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.module.watchlist.event.WatchlistCreatedEvent;
import com.stox.module.watchlist.event.WatchlistDeletedEvent;
import com.stox.module.watchlist.event.WatchlistUpdatedEvent;
import com.stox.module.watchlist.model.Watchlist;

import javafx.collections.FXCollections;

public class WatchlistComboBox extends FluentComboBox<Watchlist> {

	public WatchlistComboBox() {
		fullArea()
				.addFilter(WatchlistCreatedEvent.TYPE, this::created)
				.addFilter(WatchlistUpdatedEvent.TYPE, this::updated)
				.addFilter(WatchlistDeletedEvent.TYPE, this::deleted);
	}

	private void created(final WatchlistCreatedEvent event) {
		items().add(event.watchlist());
		FXCollections.sort(items());
	}

	private void updated(final WatchlistUpdatedEvent event) {
		final Watchlist old = items().stream().filter(item -> Objects.equals(event.watchlist().getId(), item.getId())).findFirst().orElse(null);
		items().remove(old);
		items().add(event.watchlist());
		FXCollections.sort(items());
	}

	private void deleted(final WatchlistDeletedEvent event) {
		items().remove(items().stream().filter(item -> Objects.equals(event.watchlist().getId(), item.getId())).findFirst().orElse(null));
	}

}
