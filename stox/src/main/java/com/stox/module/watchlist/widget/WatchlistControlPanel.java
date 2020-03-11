package com.stox.module.watchlist.widget;

import java.util.List;
import java.util.Optional;

import com.stox.fx.fluent.beans.binding.FluentStringBinding;
import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.search.Selector;
import com.stox.module.watchlist.event.WatchlistDeletedEvent;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.workbench.modal.ConfirmationModal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import lombok.NonNull;

public class WatchlistControlPanel extends FluentHBox {

	private final FxMessageSource messageSource;
	private final WatchlistRepository watchlistRepository;
	private final FluentComboBox<Watchlist> watchlistComboBox = new WatchlistComboBox().classes("primary", "inverted", "first");
	// TODO move this to it's own class - WatchlistClearButton
	private final FluentButton clearButton = new FluentButton().text(Icon.ERASER).onAction(this::clear).classes("icon", "primary", "inverted", "middle");

	public WatchlistControlPanel(
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository) {
		this.messageSource = messageSource;
		this.watchlistRepository = watchlistRepository;
		final FluentButton editButton = new WatchlistEditButton(messageSource, watchlistComboBox::value, watchlistRepository).classes("icon", "primary", "inverted", "middle");
		final FluentButton deleteButton = new WatchlistDeleteButton(messageSource, watchlistComboBox::value, watchlistRepository).classes("icon", "primary", "inverted", "last");
		children(watchlistComboBox, editButton, clearButton, deleteButton).fullArea().classes("box");
	}

	public WatchlistControlPanel select(final Watchlist watchlist) {
		final Watchlist effectiveWatchlist = Optional.ofNullable(watchlist)
				.orElse(watchlistComboBox.getItems().isEmpty() ? null : watchlistComboBox.getItems().get(0));
		Selector.of(effectiveWatchlist).select(watchlistComboBox.getSelectionModel());
		return this;
	}

	public Watchlist watchlist() {
		return watchlistComboBox.value();
	}

	// TODO this should be inside watchlist combo box, it should be self sufficient
	public WatchlistControlPanel watchlists(@NonNull final List<Watchlist> watchlists) {
		watchlistComboBox.items(watchlists);
		return this;
	}

	public WatchlistControlPanel watchlistChangeListener(ChangeListener<? super Watchlist> listener) {
		watchlistComboBox.valueProperty().addListener(listener);
		return this;
	}

	private void clear(final ActionEvent event) {

	}

	

}
