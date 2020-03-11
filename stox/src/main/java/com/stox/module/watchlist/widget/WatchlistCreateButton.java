package com.stox.module.watchlist.widget;


import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.watchlist.modal.WatchlistCreateModal;
import com.stox.module.watchlist.repository.WatchlistRepository;

import javafx.event.ActionEvent;
import lombok.NonNull;

public class WatchlistCreateButton extends FluentButton {

	private final FxMessageSource messageSource;
	private final WatchlistRepository watchlistRepository;
	
	public WatchlistCreateButton(
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository) {
		super(Icon.PLUS);
		this.messageSource = messageSource;
		this.watchlistRepository = watchlistRepository;
		classes("icon","success","primary","middle");
		onAction(this::action);
	}

	private void action(final ActionEvent event) {
		new WatchlistCreateModal(messageSource, watchlistRepository).show(this);
	}

}
