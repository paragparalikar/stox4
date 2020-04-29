package com.stox.module.watchlist.modal;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.form.TextFormField;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.util.Strings;
import com.stox.workbench.modal.ActionModal;

import javafx.beans.binding.StringExpression;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class WatchlistModal extends ActionModal<WatchlistModal> {

	private final Watchlist watchlist;
	private final FxMessageSource messageSource;
	private final Consumer<Watchlist> watchlistConsumer;
	private final Function<Watchlist, Set<String>> watchlistValidator;
	private final TextFormField nameFormField = new TextFormField().mandatory();
	private final FluentBorderPane container = new FluentBorderPane(nameFormField.getNode()).classes("padded", "content").fullArea();

	@Override
	public WatchlistModal show(Node caller) {
		populate();
		nameFormField.name(messageSource.get("Name"));
		graphic(Icon.BOOKMARK)
				.content(container)
				.cancelButtonText(messageSource.get("Cancel"));
		return super.show(caller);
	}

	@Override
	protected void action() {
		final String name = nameFormField.value();
		if(Strings.hasText(name)) {
			watchlist.name(name);
			final Set<String> messages = watchlistValidator.apply(watchlist);
			if (messages.isEmpty()) {
				watchlistConsumer.accept(watchlist);
				hide();
			} else {
				messages.stream()
						.map(messageSource::get)
						.collect(Collectors.reducing(this::join))
						.ifPresent(nameFormField::error);
			}
		}
	}

	private ObservableValue<String> join(final ObservableValue<String> one, final ObservableValue<String> two) {
		return StringExpression.stringExpression(one).concat(System.lineSeparator()).concat(two);
	}

	private void populate() {
		nameFormField.value(watchlist.name());
	}

	@Override
	public double initialHeight() {
		return 200;
	}

	@Override
	protected WatchlistModal getThis() {
		return this;
	}

}
