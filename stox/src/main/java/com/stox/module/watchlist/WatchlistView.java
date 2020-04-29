package com.stox.module.watchlist;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.listener.RootBinderSceneChangeListener;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.watchlist.event.FilterChangedEvent;
import com.stox.module.watchlist.event.WatchlistEntryCreatedEvent;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.module.watchlist.repository.WatchlistEntryRepository;
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
	private final WatchlistEntryRepository watchlistEntryRepository;
	private final SearchableListView<WatchlistEntry> listView = new SearchableListView<>();
	private final ChangeListener<Scene> sceneChangeListener = new RootBinderSceneChangeListener<>(WatchlistEntryCreatedEvent.TYPE, this::watchlistEntryCreated);
	
	public WatchlistView(
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository, 
			@NonNull final WatchlistEntryRepository watchlistEntryRepository) {
		this.watchlistEntryRepository = watchlistEntryRepository;
		title(titleBar = new WatchlistTitleBar(messageSource, listView, watchlistRepository, watchlistEntryRepository));
		titleBar.getNode().addEventHandler(FilterChangedEvent.TYPE, this::filterChanged);
		getNode().sceneProperty().addListener(new WeakChangeListener<>(sceneChangeListener));
		listView.setCellFactory(value -> new WatchlistEntryListCell(watchlistEntryRepository));
		content(listView);
	}
	
	private void watchlistEntryCreated(final WatchlistEntryCreatedEvent event) {
		final Integer watchlistId = event.getWatchlistEntry().getWatchlistId();
		Optional.ofNullable(titleBar.selected())
			.map(Watchlist::getId)
			.filter(Predicate.isEqual(watchlistId))
			.ifPresent(id -> {
				listView.getItems().add(event.getWatchlistEntry());
				FXCollections.sort(listView.getItems());
			});
	}
	
	@Override
	public WatchlistView start(WatchlistViewState state, Bounds bounds) {
		super.start(state, bounds);
		titleBar.state(state).bind();
		return this;
	}

	@Override
	public WatchlistViewState stop(Bounds bounds) {
		return stop(titleBar.state(), bounds);
	}

	private void filterChanged(final FilterChangedEvent event) {
		final Set<WatchlistEntry> entries = watchlistEntryRepository.findAll().stream()
			.filter(event.predicate()).collect(Collectors.toSet());
		listView.getItems().setAll(entries);
	}
	
}
