package com.stox.module.watchlist;

import java.util.Optional;
import java.util.function.Predicate;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.listener.CompositeChangeListener;
import com.stox.fx.widget.listener.RootBinderSceneChangeListener;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.watchlist.event.WatchlistClearedEvent;
import com.stox.module.watchlist.event.WatchlistEntryCreatedEvent;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.module.watchlist.widget.WatchlistEntryListCell;
import com.stox.workbench.module.ModuleView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.NonNull;

public class WatchlistView extends ModuleView<WatchlistViewState> {

	@Getter
	private final WatchlistTitleBar titleBar;
	private final SearchableListView<WatchlistEntry> listView = new SearchableListView<>();
	private final ChangeListener<Scene> sceneChangeListener = new CompositeChangeListener<>(
			new RootBinderSceneChangeListener<>(WatchlistClearedEvent.TYPE, this::watchlistCleared),
			new RootBinderSceneChangeListener<>(WatchlistEntryCreatedEvent.TYPE, this::watchlistEntryCreated));
	
	public WatchlistView(
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository) {
		title(titleBar = new WatchlistTitleBar(messageSource, listView, watchlistRepository));
		getNode().sceneProperty().addListener(new WeakChangeListener<>(sceneChangeListener));
		listView.setCellFactory(value -> new WatchlistEntryListCell());
		content(listView);
	}
	
	private void watchlistCleared(final WatchlistClearedEvent event) {
		Optional.ofNullable(titleBar.selected())
			.filter(Predicate.isEqual(event.watchlist()))
			.ifPresent(id -> {
				listView.getItems().clear();
			});
	}
	
	private void watchlistEntryCreated(final WatchlistEntryCreatedEvent event) {
		Optional.ofNullable(titleBar.selected())
			.filter(Predicate.isEqual(event.watchlist()))
			.ifPresent(id -> {
				listView.getItems().add(event.watchlistEntry());
				FXCollections.sort(listView.getItems());
			});
	}
	
	@Override
	public WatchlistView start(WatchlistViewState state, Bounds bounds) {
		super.start(state, bounds);
		Optional.ofNullable(state).ifPresent(titleBar::state);
		titleBar.bind();
		return this;
	}

	@Override
	public WatchlistViewState stop(Bounds bounds) {
		return stop(titleBar.state(), bounds);
	}
	
}
