package com.stox.module.watchlist.modal;

import com.stox.fx.widget.FxMessageSource;

import lombok.NonNull;

public class WatchlistCreateModal extends WatchlistModal<WatchlistCreateModal> {

	public WatchlistCreateModal(@NonNull final FxMessageSource messageSource) {
		super(messageSource);
		actionButtonText(messageSource.get("Create"));
		title(messageSource.get("Create New Watchlist"));
	}

	@Override
	protected void action(@NonNull final String name) {
		
	}

	@Override
	protected WatchlistCreateModal getThis() {
		return this;
	}

}
