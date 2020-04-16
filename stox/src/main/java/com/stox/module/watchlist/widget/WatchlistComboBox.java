package com.stox.module.watchlist.widget;

import java.util.Objects;

import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.widget.listener.CompositeChangeListener;
import com.stox.fx.widget.listener.RootBinderSceneChangeListener;
import com.stox.module.watchlist.event.WatchlistCreatedEvent;
import com.stox.module.watchlist.event.WatchlistDeletedEvent;
import com.stox.module.watchlist.event.WatchlistUpdatedEvent;
import com.stox.module.watchlist.model.Watchlist;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.Scene;

public class WatchlistComboBox extends FluentComboBox<Watchlist> {
	
	private final ChangeListener<Scene> sceneChangeListener = new CompositeChangeListener<>(
			new RootBinderSceneChangeListener<>(WatchlistCreatedEvent.TYPE, this::created),
			new RootBinderSceneChangeListener<>(WatchlistUpdatedEvent.TYPE, this::updated),
			new RootBinderSceneChangeListener<>(WatchlistDeletedEvent.TYPE, this::deleted));
	
	public WatchlistComboBox() {
		fullArea().sceneProperty().addListener(new WeakChangeListener<>(sceneChangeListener));
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
