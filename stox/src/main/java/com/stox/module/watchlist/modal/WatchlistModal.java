package com.stox.module.watchlist.modal;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.form.TextFormField;
import com.stox.util.StringUtil;
import com.stox.workbench.modal.ActionModal;

import lombok.NonNull;

public abstract class WatchlistModal<T extends WatchlistModal<T>> extends ActionModal<T> {

	private final FxMessageSource messageSource;
	private final TextFormField nameFormField = new TextFormField().mandatory();

	protected abstract void action(String name);
	
	public WatchlistModal(@NonNull final FxMessageSource messageSource) {
		this.messageSource = messageSource;
		graphic(Icon.BOOKMARK)
			.content(nameFormField.name(messageSource.get("Name")).getNode())
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
	
}
