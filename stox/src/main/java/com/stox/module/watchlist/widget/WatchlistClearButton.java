package com.stox.module.watchlist.widget;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import com.stox.fx.fluent.beans.binding.FluentStringBinding;
import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.workbench.modal.ConfirmationModal;

import javafx.beans.value.ObservableValue;

public class WatchlistClearButton extends FluentButton{
	
	private final FxMessageSource messageSource;
	private final Supplier<Watchlist> watchlistSupplier;
	
	public WatchlistClearButton(
			final FxMessageSource messageSource,
			final Supplier<Watchlist> watchlistSupplier) {
		this.messageSource = messageSource;
		this.watchlistSupplier = watchlistSupplier;
		onAction(event -> confirm()).text(Icon.ERASER);
	}

	private void confirm() {
		Optional.ofNullable(watchlistSupplier.get()).ifPresent(watchlist -> {
			final ObservableValue<String> message = messageSource.get("Are you sure you want to CLEAR watchlist");
			new ConfirmationModal(() -> clear(watchlist))
					.title(messageSource.get("Please Confirm"))
					.message(new FluentStringBinding(() -> {
						return message.getValue() + " - " + watchlist.name();
					}, message))
					.cancelButtonText(messageSource.get("Cancel"))
					.actionButtonText(messageSource.get("Clear"))
					.show(this);
		});
	}
	
	private void clear(final Watchlist watchlist) {
		watchlist.entries().values().forEach(Collection::clear);
	}
	
}
