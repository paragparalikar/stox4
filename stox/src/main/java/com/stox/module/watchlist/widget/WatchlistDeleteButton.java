package com.stox.module.watchlist.widget;

import java.util.Optional;
import java.util.function.Supplier;

import com.stox.fx.fluent.beans.binding.FluentStringBinding;
import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.watchlist.event.WatchlistDeletedEvent;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.workbench.modal.ConfirmationModal;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import lombok.NonNull;

public class WatchlistDeleteButton extends FluentButton {

	private final FxMessageSource messageSource;
	private final Supplier<Watchlist> watchlistSupplier;
	private final WatchlistRepository watchlistRepository;
	
	public WatchlistDeleteButton(
			@NonNull final FxMessageSource messageSource,
			@NonNull final Supplier<Watchlist> watchlistSupplier,
			@NonNull final WatchlistRepository watchlistRepository) {
		this.messageSource = messageSource;
		this.watchlistSupplier = watchlistSupplier;
		this.watchlistRepository = watchlistRepository;
		text(Icon.TRASH).onAction(this::delete);
	}
	
	private void delete(final ActionEvent event) {
		Optional.ofNullable(watchlistSupplier.get()).ifPresent(watchlist -> {
			final ObservableValue<String> message = messageSource.get("Are you sure you want to DELETE watchlist");
			new ConfirmationModal(() -> delete(watchlist))
					.title(messageSource.get("Please Confirm"))
					.message(new FluentStringBinding(() -> {
						return message.getValue() + " - " + watchlist.getName();
					}, message))
					.cancelButtonText(messageSource.get("Cancel"))
					.actionButtonText(messageSource.get("Delete"))
					.show(this);
		});
	}

	private void delete(final Watchlist watchlist) {
		watchlistRepository.delete(watchlist.getId());
		fireEvent(new WatchlistDeletedEvent(watchlist));
	}
}
