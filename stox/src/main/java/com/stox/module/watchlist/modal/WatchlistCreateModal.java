package com.stox.module.watchlist.modal;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.watchlist.event.WatchlistCreatedEvent;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.repository.WatchlistRepository;

import lombok.NonNull;

public class WatchlistCreateModal extends WatchlistModal<WatchlistCreateModal> {

	private final WatchlistRepository watchlistRepository;
	
	public WatchlistCreateModal(
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository) {
		super(messageSource);
		this.watchlistRepository = watchlistRepository;
		actionButtonText(messageSource.get("Create"));
		title(messageSource.get("Create New Watchlist"));
	}

	@Override
	protected void action(@NonNull final String name) {
		final Watchlist watchlist = new Watchlist();
		watchlist.setName(name);
		watchlistRepository.save(watchlist);
		getNode().fireEvent(new WatchlistCreatedEvent(watchlist));
	}

	@Override
	protected WatchlistCreateModal getThis() {
		return this;
	}

}
