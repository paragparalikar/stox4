package com.stox.module.watchlist.modal;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.form.TextFormField;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.util.StringUtil;
import com.stox.workbench.modal.ActionModal;

import lombok.NonNull;

public abstract class WatchlistModal<T extends WatchlistModal<T>> extends ActionModal<T> {

	private final FxMessageSource messageSource;
	private final TextFormField nameFormField = new TextFormField().mandatory();
	private final FluentBorderPane container = new FluentBorderPane(nameFormField.getNode()).classes("padded", "content").fullArea();

	protected abstract void action(String name);
	
	public WatchlistModal(@NonNull final FxMessageSource messageSource) {
		this.messageSource = messageSource;
		nameFormField.name(messageSource.get("Name"));
		graphic(Icon.BOOKMARK)
			.content(container)
			.cancelButtonText(messageSource.get("Cancel"));
	}

	@Override
	protected void action() {
		final String text = nameFormField.value();
		if(StringUtil.hasText(text)) {
			action(text);
			hide();
		}else {
			nameFormField.error(messageSource.get("Name is required"));
		}
	}
	
	protected T populate(@NonNull final Watchlist watchlist) {
		nameFormField.value(watchlist.getName());
		return getThis();
	}
	
	@Override
	public double initialHeight() {
		return 200;
	}
	
}
