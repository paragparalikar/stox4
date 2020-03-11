package com.stox.module.watchlist;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.search.SearchBox;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.fx.widget.search.Selector;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.CoreConstant;
import com.stox.module.watchlist.event.FilterChangedEvent;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.module.watchlist.repository.WatchlistEntryRepository;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.link.LinkState;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.Toggle;
import lombok.NonNull;

public class WatchlistTitleBar extends ModuleTitleBar {

	private final WatchlistRepository watchlistRepository;
	private final WatchlistEntryRepository watchlistEntryRepository;
	
	private final Toggle searchToggle;
	private final SearchBox<WatchlistEntry> searchBox;
	private final SearchableListView<WatchlistEntry> listView;
	private final LinkButton linkButton = new LinkButton();
	private final FluentComboBox<Watchlist> watchlistComboBox = new FluentComboBox<Watchlist>().classes("primary", "inverted").fullWidth();
	private final FluentComboBox<BarSpan> barSpanComboBox = new FluentComboBox<BarSpan>().items(BarSpan.values()).classes("primary", "inverted").fullWidth();

	public WatchlistTitleBar(@NonNull final SearchableListView<WatchlistEntry> listView,
			@NonNull final WatchlistRepository watchlistRepository,
			@NonNull final WatchlistEntryRepository watchlistEntryRepository) {
		this.listView = listView;
		this.watchlistRepository = watchlistRepository;
		this.watchlistEntryRepository = watchlistEntryRepository;
		this.searchBox = new SearchBox<WatchlistEntry>(listView, this::test);
		getTitleBar().append(Side.RIGHT, linkButton);
		getTitleBar().append(Side.BOTTOM, watchlistComboBox);
		getTitleBar().append(Side.BOTTOM, barSpanComboBox);
		searchToggle = appendToggleNode(Icon.SEARCH, searchBox.getNode());
	}

	private boolean test(final WatchlistEntry entry, String text) {
		text = text.trim().toLowerCase();
		final Scrip scrip = null == entry ? null : entry.getScrip();
		return null != scrip && (scrip.getName().trim().toLowerCase().contains(text) ||
				scrip.getCode().trim().toLowerCase().contains(text) ||
				scrip.getIsin().trim().toLowerCase().contains(text));
	}

	WatchlistTitleBar select(final Watchlist watchlist) {
		final Watchlist effectiveWatchlist = Optional.ofNullable(watchlist)
				.orElse(watchlistComboBox.getItems().isEmpty() ? null : watchlistComboBox.getItems().get(0));
		Selector.of(effectiveWatchlist).select(watchlistComboBox.getSelectionModel());
		return this;
	}

	WatchlistTitleBar bind() {
		barSpanComboBox.selectionModel().selectedItemProperty().addListener(this::barSpan);
		watchlistComboBox.selectionModel().selectedItemProperty().addListener(this::watchlist);
		listView.getSelectionModel().selectedItemProperty().addListener(
				(o, old, entry) -> linkButton.getLink().setState(LinkState.builder()
						.put(CoreConstant.KEY_TO, String.valueOf(0))
						.put(CoreConstant.KEY_BARSPAN, barSpanComboBox.value().getShortName())
						.put(CoreConstant.KEY_ISIN, null == entry ? null : entry.getScrip().getIsin())
						.build()));
		return this;
	}

	private void barSpan(final ObservableValue<? extends BarSpan> observable, final BarSpan old, final BarSpan barSpan) {
		filterChanged(barSpan, watchlistComboBox.value());
	}

	private void watchlist(final ObservableValue<? extends Watchlist> observable, final Watchlist old, final Watchlist watchlist) {
		filterChanged(barSpanComboBox.value(), watchlist);
	}

	private void filterChanged(@NonNull final BarSpan barSpan, final Watchlist watchlist) {
		final Predicate<WatchlistEntry> barSpanPredicate = entry -> Objects.equals(barSpan, entry.getBarSpan());
		final Predicate<WatchlistEntry> watchlistPredicate = entry -> Objects.equals(entry.getWatchlistId(), watchlist.getId());
		final Predicate<WatchlistEntry> effectivePredicate = Optional.ofNullable(watchlist).map(w -> barSpanPredicate.and(watchlistPredicate)).orElse(barSpanPredicate);
		getNode().fireEvent(new FilterChangedEvent(effectivePredicate));
	}

	WatchlistViewState state() {
		return new WatchlistViewState()
				.barSpan(barSpanComboBox.value())
				.watchlistId(Optional.ofNullable(watchlistComboBox.value()).map(Watchlist::getId).orElse(null))
				.entryId(Optional.ofNullable(listView.getSelectionModel().getSelectedItem()).map(WatchlistEntry::getId).orElse(null))
				.searchText(searchBox.text())
				.searchVisible(searchToggle.isSelected());
	}

	WatchlistTitleBar state(final WatchlistViewState state) {
		final Optional<WatchlistViewState> optionalState = Optional.ofNullable(state);
		barSpanComboBox.select(optionalState.map(WatchlistViewState::barSpan).orElse(BarSpan.D));
		searchBox.text(optionalState.map(WatchlistViewState::searchText).orElse(null));
		searchToggle.setSelected(optionalState.map(WatchlistViewState::searchVisible).orElse(Boolean.FALSE));

		watchlistComboBox.getItems().setAll(watchlistRepository.findAll());
		final Watchlist watchlist = watchlistRepository.find(Optional.ofNullable(state).map(WatchlistViewState::watchlistId).orElse(0));
		final Watchlist effectiveWatchlist = Optional.ofNullable(watchlist)
				.orElse(watchlistComboBox.getItems().isEmpty() ? null : watchlistComboBox.getItems().get(0));
		Selector.of(effectiveWatchlist).select(watchlistComboBox.getSelectionModel());
		filterChanged(barSpanComboBox.value(), watchlistComboBox.value());
		
		final WatchlistEntry entry = watchlistEntryRepository.find(Optional.ofNullable(state).map(WatchlistViewState::entryId).orElse(0));
		Selector.of(entry).select(listView.getSelectionModel());
		return this;
	}

}
