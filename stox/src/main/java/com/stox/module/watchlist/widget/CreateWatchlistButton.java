package com.stox.module.watchlist.widget;


import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.watchlist.modal.WatchlistCreateModal;

import javafx.event.ActionEvent;
import lombok.NonNull;

public class CreateWatchlistButton extends FluentButton {

	private final FxMessageSource messageSource;
	
	public CreateWatchlistButton(@NonNull final FxMessageSource messageSource) {
		super(Icon.PLUS);
		this.messageSource = messageSource;
		classes("icon","success","primary","middle");
		onAction(this::action);
	}

	private void action(final ActionEvent event) {
		new WatchlistCreateModal(messageSource).show(this);
	}

}
