package com.stox.module.watchlist.widget;

import java.util.Optional;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.watchlist.modal.WatchlistEditModal;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.repository.WatchlistRepository;

import javafx.event.ActionEvent;
import lombok.NonNull;

public class WatchlistEditButton extends FluentButton {

	private final FxMessageSource messageSource;
	private final Supplier<Watchlist> watchlistSupplier;
	private final WatchlistRepository watchlistRepository;

	public WatchlistEditButton(
			@NonNull final FxMessageSource messageSource,
			@NonNull final Supplier<Watchlist> watchlistSupplier,
			@NonNull final WatchlistRepository watchlistRepository) {
		this.messageSource = messageSource;
		this.watchlistSupplier = watchlistSupplier;
		this.watchlistRepository = watchlistRepository;
		text(Icon.EDIT).onAction(this::edit);
	}

	private void edit(final ActionEvent event) {
		Optional.ofNullable(watchlistSupplier.get()).ifPresent(watchlist -> new WatchlistEditModal(watchlist, messageSource, watchlistRepository).show(this));
	}
}
