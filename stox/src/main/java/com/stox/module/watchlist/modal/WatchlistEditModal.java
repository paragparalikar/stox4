package com.stox.module.watchlist.modal;

import com.stox.fx.fluent.beans.binding.FluentStringBinding;
import com.stox.fx.widget.FxMessageSource;
import com.stox.module.watchlist.event.WatchlistUpdatedEvent;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.repository.WatchlistRepository;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class WatchlistEditModal extends WatchlistModal<WatchlistEditModal> {

	private final Watchlist watchlist;
	private final FxMessageSource messageSource;
	private final WatchlistRepository watchlistRepository;
	
	public WatchlistEditModal(
			@NonNull final Watchlist watchlist,
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository) {
		super(messageSource);
		this.watchlist = watchlist;
		this.messageSource = messageSource;
		this.watchlistRepository = watchlistRepository;
		actionButtonText(messageSource.get("Edit"));
		populate(watchlist);
	}
	
	@Override
	protected WatchlistEditModal populate(Watchlist watchlist) {
		final ObservableValue<String> value = messageSource.get("Modify Watchlist");
		title(new FluentStringBinding(() -> {
			return value.getValue() + " - " + watchlist.getName();
		}, value));
		return super.populate(watchlist);
	}
	
	@Override
	protected void action(@NonNull final String name) {
		watchlist.setName(name);
		watchlistRepository.update(watchlist);
		getNode().fireEvent(new WatchlistUpdatedEvent(watchlist));
	}

	@Override
	protected WatchlistEditModal getThis() {
		return this;
	}

}
