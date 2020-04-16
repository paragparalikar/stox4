package com.stox.module.watchlist.widget;

import java.util.Optional;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.search.Selector;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.repository.WatchlistEntryRepository;
import com.stox.module.watchlist.repository.WatchlistRepository;

import javafx.beans.property.ReadOnlyObjectProperty;
import lombok.NonNull;

public class WatchlistControlPanel extends FluentHBox {

	private final FluentComboBox<Watchlist> watchlistComboBox = new WatchlistComboBox().classes("primary", "inverted", "first");

	public WatchlistControlPanel(
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository,
			@NonNull final WatchlistEntryRepository watchlistEntryRepository) {
		watchlistComboBox.items(watchlistRepository.findAll());
		final Supplier<Watchlist> watchlistSupplier = watchlistComboBox::value;
		final FluentButton editButton = new WatchlistEditButton(messageSource, watchlistSupplier, watchlistRepository).classes("icon", "primary", "inverted", "middle");
		final FluentButton clearButton = new WatchlistClearButton(messageSource, watchlistSupplier, watchlistEntryRepository).classes("icon", "primary", "inverted", "middle");
		final FluentButton deleteButton = new WatchlistDeleteButton(messageSource, watchlistSupplier, watchlistRepository).classes("icon", "primary", "inverted", "last");
		children(watchlistComboBox, editButton, clearButton, deleteButton).fullArea().classes("box");
	}

	public WatchlistControlPanel select(final Watchlist watchlist) {
		final Watchlist effectiveWatchlist = Optional.ofNullable(watchlist)
				.orElse(watchlistComboBox.getItems().isEmpty() ? null : watchlistComboBox.getItems().get(0));
		Selector.of(effectiveWatchlist).select(watchlistComboBox.getSelectionModel());
		return this;
	}
	
	public ReadOnlyObjectProperty<Watchlist> watchlistProperty(){
		return watchlistComboBox.valueProperty();
	}

}
