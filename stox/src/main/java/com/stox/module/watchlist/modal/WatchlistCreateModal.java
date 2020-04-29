package com.stox.module.watchlist.modal;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.watchlist.event.WatchlistCreatedEvent;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.repository.WatchlistRepository;

import javafx.scene.Node;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Builder
@RequiredArgsConstructor
public class WatchlistCreateModal {

	@NonNull
	private final FxMessageSource messageSource;

	@NonNull
	private final WatchlistRepository watchlistRepository;

	public void show(@NonNull final Node caller) {
		WatchlistModal.builder()
				.watchlist(new Watchlist())
				.messageSource(messageSource)
				.watchlistValidator(this::validate)
				.watchlistConsumer(watchlist -> save(watchlist, caller))
				.build()
				.actionButtonText(messageSource.get("Create"))
				.title(messageSource.get("Create New Watchlist"))
				.show(caller);
	}

	private Set<String> validate(@NonNull final Watchlist watchlist) {
		return Optional.of(watchlist.name())
			.filter(watchlistRepository::exists)
			.map(name -> String.format("Watchlist with name %s already exists", name))
			.map(Collections::singleton)
			.orElse(Collections.emptySet());
	}

	private void save(@NonNull final Watchlist watchlist, @NonNull final Node caller) {
		watchlistRepository.save(watchlist);
		caller.fireEvent(new WatchlistCreatedEvent(watchlist));
	}

}
