package com.stox.module.watchlist.widget;

import java.util.List;
import java.util.Optional;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.search.Selector;
import com.stox.module.watchlist.modal.WatchlistEditModal;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.repository.WatchlistRepository;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import lombok.NonNull;

public class WatchlistControlPanel extends FluentHBox{

	private final FxMessageSource messageSource;
	private final WatchlistRepository watchlistRepository;
	private final FluentComboBox<Watchlist> watchlistComboBox = new WatchlistComboBox().classes("primary", "inverted", "first");
	private final FluentButton editButton = new FluentButton().text(Icon.EDIT).onAction(this::edit).classes("icon","primary","inverted","middle");
	private final FluentButton clearButton = new FluentButton().text(Icon.ERASER).onAction(this::clear).classes("icon","primary","inverted","middle");
	private final FluentButton deleteButton = new FluentButton().text(Icon.TRASH).onAction(this::delete).classes("icon","primary","inverted","last");
	
	public WatchlistControlPanel(
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository) {
		this.messageSource = messageSource;
		this.watchlistRepository = watchlistRepository;
		children(watchlistComboBox, editButton, clearButton, deleteButton).fullArea().classes("box");
	}

	public WatchlistControlPanel select(final Watchlist watchlist){
		final Watchlist effectiveWatchlist = Optional.ofNullable(watchlist)
				.orElse(watchlistComboBox.getItems().isEmpty() ? null : watchlistComboBox.getItems().get(0));
		Selector.of(effectiveWatchlist).select(watchlistComboBox.getSelectionModel());
		return this;
	}
	
	public Watchlist watchlist() {
		return watchlistComboBox.value();
	}
	
	public WatchlistControlPanel watchlists(@NonNull final List<Watchlist> watchlists) {
		watchlistComboBox.items(watchlists);
		return this;
	}
	
	public WatchlistControlPanel watchlistChangeListener(ChangeListener<? super Watchlist> listener) {
		watchlistComboBox.valueProperty().addListener(listener);
		return this;
	}
	
	private void edit(final ActionEvent event) {
		new WatchlistEditModal(watchlist(), messageSource, watchlistRepository).show(this);
	}
	
	private void clear(final ActionEvent event) {
		
	}
	
	private void delete(final ActionEvent event) {
		
	}
	
}
