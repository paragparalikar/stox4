package com.stox.module.watchlist;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.form.TextFormField;
import com.stox.util.StringUtil;
import com.stox.workbench.modal.ActionModal;

import lombok.NonNull;

public class WatchlistEditorModal extends ActionModal<WatchlistEditorModal> {

	private final FxMessageSource messageSource;
	private final TextFormField nameFormField = new TextFormField().mandatory();

	public WatchlistEditorModal(@NonNull final FxMessageSource messageSource) {
		this.messageSource = messageSource;
		graphic(Icon.BOOKMARK)
			.title(messageSource.get("Watchlists"))
			.content(nameFormField.name(messageSource.get("Name")).getNode())
			.actionButtonText(messageSource.get("Ok"))
			.cancelButtonText(messageSource.get("Cancel"));
	}

	@Override
	protected WatchlistEditorModal getThis() {
		return this;
	}

	@Override
	protected void action() {
		final String text = nameFormField.value();
		if(StringUtil.hasText(text)) {
			
			hide();
		}else {
			nameFormField.error(messageSource.get("Name is required"));
		}
	}

}
