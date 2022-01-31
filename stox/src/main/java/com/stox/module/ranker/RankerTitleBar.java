package com.stox.module.ranker;

import java.util.Optional;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.ranker.modal.RankerConfigEditorModal;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.event.ActionEvent;
import javafx.geometry.Side;
import lombok.NonNull;

public class RankerTitleBar extends ModuleTitleBar {

	private final RankerConfig rankerConfig;
	private final FxMessageSource messageSource;
	private RankerConfigEditorModal rankerConfigEditorModal;
	private final FluentButton configButton = new FluentButton(Icon.GEAR)
				.classes("primary", "icon")
				.addHandler(ActionEvent.ACTION, event -> configEditor());

	public RankerTitleBar(
			@NonNull final RankerConfig rankerConfig,
			@NonNull final FxMessageSource messageSource) {
		this.messageSource = messageSource;
		this.rankerConfig = rankerConfig;
		getTitleBar().append(Side.RIGHT, configButton);
	}

	private void configEditor() {
		Optional.ofNullable(rankerConfigEditorModal)
			.orElseGet(() -> rankerConfigEditorModal = new RankerConfigEditorModal(rankerConfig, messageSource))
			.show(getNode());
	}
	
}
