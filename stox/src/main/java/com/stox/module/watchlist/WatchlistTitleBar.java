package com.stox.module.watchlist;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.search.SearchBox;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.fx.widget.search.Selector;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.CoreConstant;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.module.watchlist.widget.WatchlistControlPanel;
import com.stox.module.watchlist.widget.WatchlistCreateButton;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.link.LinkState;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.Toggle;
import lombok.NonNull;

public class WatchlistTitleBar extends ModuleTitleBar {

	private final WatchlistRepository watchlistRepository;
	
	private final Toggle searchToggle;
	private final SearchBox<WatchlistEntry> searchBox;
	private final SearchableListView<WatchlistEntry> listView;
	private final LinkButton linkButton = new LinkButton();
	private final WatchlistControlPanel controlPanel;
	private final FluentComboBox<BarSpan> barSpanComboBox = new FluentComboBox<BarSpan>()
			.items(BarSpan.values()).classes("primary", "inverted").fullWidth().select(BarSpan.D);

	public WatchlistTitleBar(
			@NonNull final FxMessageSource messageSource,
			@NonNull final SearchableListView<WatchlistEntry> listView,
			@NonNull final WatchlistRepository watchlistRepository) {
		this.listView = listView;
		this.watchlistRepository = watchlistRepository;
		this.searchBox = new SearchBox<WatchlistEntry>(listView, this::test);
		getTitleBar().append(Side.RIGHT, linkButton);
		getTitleBar().append(Side.BOTTOM, barSpanComboBox);
		getTitleBar().append(Side.BOTTOM, controlPanel = new WatchlistControlPanel(messageSource, watchlistRepository));
		searchToggle = appendToggleNode(Icon.SEARCH, searchBox.getNode());
		getTitleBar().append(Side.RIGHT, new WatchlistCreateButton(messageSource, watchlistRepository));
	}

	private boolean test(final WatchlistEntry entry, String text) {
		text = text.trim().toLowerCase();
		final Scrip scrip = null == entry ? null : entry.scrip();
		return null != scrip && (scrip.name().trim().toLowerCase().contains(text) ||
				scrip.code().trim().toLowerCase().contains(text) ||
				scrip.isin().trim().toLowerCase().contains(text));
	}

	WatchlistTitleBar select(final Watchlist watchlist) {
		controlPanel.select(watchlist);
		return this;
	}
	
	Watchlist selected() {
		return controlPanel.watchlistProperty().get();
	}

	WatchlistTitleBar bind() {
		barSpanComboBox.selectionModel().selectedItemProperty().addListener(this::barSpan);
		controlPanel.watchlistProperty().addListener(this::watchlist);
		listView.getSelectionModel().selectedItemProperty().addListener(this::watchlistEntrySelectionChanged);
		return this;
	}
	
	private void watchlistEntrySelectionChanged(final ObservableValue<? extends WatchlistEntry> observableValue, 
			final WatchlistEntry oldValue, final WatchlistEntry newValue) {
		Optional.ofNullable(newValue).ifPresent(watchlistEntry -> linkButton.getLink().setState(LinkState.builder()
				.put(CoreConstant.KEY_TO, String.valueOf(0))
				.put(CoreConstant.KEY_BARSPAN, barSpanComboBox.value().shortName())
				.put(CoreConstant.KEY_ISIN, watchlistEntry.scrip().isin())
				.build()));
	}

	private void barSpan(final ObservableValue<? extends BarSpan> observable, final BarSpan old, final BarSpan barSpan) {
		filterChanged(barSpan, controlPanel.watchlistProperty().get());
	}

	private void watchlist(final ObservableValue<? extends Watchlist> observable, final Watchlist old, final Watchlist watchlist) {
		listView.setUserData(watchlist);
		filterChanged(barSpanComboBox.value(), watchlist);
	}

	private void filterChanged(@NonNull final BarSpan barSpan, final Watchlist watchlist) {
		final List<WatchlistEntry> watchlistEntries = Optional.ofNullable(watchlist)
			.map(Watchlist::entries)
			.map(entries -> entries.get(barSpan))
			.orElse(Collections.emptyList());
		watchlistEntries.sort(Comparator.naturalOrder());
		listView.setUserData(watchlist);
		listView.getItems().setAll(watchlistEntries);
	}

	WatchlistViewState state() {
		return new WatchlistViewState()
				.barSpan(barSpanComboBox.value())
				.name(Optional.ofNullable(controlPanel.watchlistProperty().get()).map(Watchlist::name).orElse(null))
				.isin(Optional.ofNullable(listView.getSelectionModel().getSelectedItem()).map(WatchlistEntry::scrip).map(Scrip::isin).orElse(null))
				.searchText(searchBox.text())
				.searchVisible(searchToggle.isSelected());
	}

	WatchlistTitleBar state(@NonNull final WatchlistViewState state) {
		searchBox.text(state.searchText());
		searchToggle.setSelected(state.searchVisible());
		barSpanComboBox.select(null == state.barSpan() ? BarSpan.D : state.barSpan());

		if(Objects.nonNull(state.name())) {
			final Watchlist watchlist = watchlistRepository.get(state.name());
			controlPanel.select(watchlist);
			filterChanged(barSpanComboBox.value(), controlPanel.watchlistProperty().get());
			if(Objects.nonNull(state.isin()) && Objects.nonNull(state.barSpan())) {
				watchlist.entries().get(state.barSpan()).stream()
						.filter(e -> e.scrip().isin().equals(state.isin()))
						.findFirst()
						.ifPresent(entry -> {
							Selector.of(entry).select(listView.getSelectionModel());
						});
			}
		}
		return this;
	}

}
