package com.stox.module.watchlist.widget;

import java.util.Objects;

import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.module.watchlist.event.WatchlistCreatedEvent;
import com.stox.module.watchlist.event.WatchlistDeletedEvent;
import com.stox.module.watchlist.event.WatchlistUpdatedEvent;
import com.stox.module.watchlist.model.Watchlist;

import javafx.collections.FXCollections;
import javafx.scene.Node;

public class WatchlistComboBox extends FluentComboBox<Watchlist> {

	public WatchlistComboBox() {
		fullArea().sceneProperty().addListener((o,old,scene) -> bind(scene.getRoot()));
	}

	private void bind(final Node node) {
		node.addEventHandler(WatchlistCreatedEvent.TYPE, this::created);
		node.addEventHandler(WatchlistUpdatedEvent.TYPE, this::updated);
		node.addEventHandler(WatchlistDeletedEvent.TYPE, this::deleted);
	}
	
	private void created(final WatchlistCreatedEvent event) {
		items().add(event.watchlist());
		FXCollections.sort(items());
		select(event.watchlist());
	}

	private void updated(final WatchlistUpdatedEvent event) {
		final Watchlist old = items().stream().filter(item -> Objects.equals(event.watchlist().getId(), item.getId())).findFirst().orElse(null);
		items().remove(old);
		items().add(event.watchlist());
		FXCollections.sort(items());
		select(event.watchlist());
	}

	private void deleted(final WatchlistDeletedEvent event) {
		items().remove(items().stream().filter(item -> Objects.equals(event.watchlist().getId(), item.getId())).findFirst().orElse(null));
		if(!items().isEmpty()) {
			select(items().get(0));
		}
	}

}
