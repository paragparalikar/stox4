package com.stox.module.watchlist;

import java.util.Set;
import java.util.stream.Collectors;

import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.watchlist.event.FilterChangedEvent;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.module.watchlist.repository.WatchlistEntryRepository;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.workbench.module.ModuleView;

import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class WatchlistView extends ModuleView<WatchlistViewState> {

	@Getter
	private final WatchlistTitleBar titleBar;
	private final WatchlistRepository watchlistRepository;
	private final WatchlistEntryRepository watchlistEntryRepository;
	private final SearchableListView<WatchlistEntry> listView = new SearchableListView<>();
	
	public WatchlistView(
			@NonNull final WatchlistRepository watchlistRepository, 
			@NonNull final WatchlistEntryRepository watchlistEntryRepository) {
		this.watchlistRepository = watchlistRepository;
		this.watchlistEntryRepository = watchlistEntryRepository;
		title(titleBar = new WatchlistTitleBar(listView, watchlistRepository, watchlistEntryRepository));
		titleBar.getNode().addEventHandler(FilterChangedEvent.TYPE, this::filterChanged);
		content(listView);
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
