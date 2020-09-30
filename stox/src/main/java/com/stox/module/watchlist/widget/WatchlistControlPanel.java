package com.stox.module.watchlist.widget;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.search.Selector;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.repository.WatchlistRepository;

import javafx.beans.property.ReadOnlyObjectProperty;
import lombok.NonNull;

public class WatchlistControlPanel extends FluentHBox {

	private final FluentComboBox<Watchlist> watchlistComboBox = new WatchlistComboBox().classes("primary", "inverted", "first");

	public WatchlistControlPanel(
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository) {
		
		final List<Watchlist> watchlists = watchlistRepository.findAll();
		watchlists.sort(Comparator.naturalOrder());
		watchlistComboBox.items(watchlists);
		
		final Supplier<Watchlist> watchlistSupplier = watchlistComboBox::value;
		final FluentButton editButton = new WatchlistEditButton(messageSource, watchlistSupplier, watchlistRepository).classes("icon", "primary", "inverted", "middle");
		final FluentButton clearButton = new WatchlistClearButton(messageSource, watchlistSupplier).classes("icon", "primary", "inverted", "middle");
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
