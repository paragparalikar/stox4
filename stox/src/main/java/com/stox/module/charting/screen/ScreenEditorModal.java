package com.stox.module.charting.screen;

import java.util.function.Consumer;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.auto.AutoUiBuilder;
import com.stox.fx.widget.auto.AutoVBox;
import com.stox.module.screen.Screen;
import com.stox.workbench.modal.ActionModal;

public class ScreenEditorModal extends ActionModal<ScreenEditorModal> {

	private final AutoVBox autoVBox;
	private final Object configuration;
	private final Consumer<Object> configurationConsumer;

	public ScreenEditorModal(
			final FxMessageSource messageSource,
			final Object configuration,
			final Screen<?> screen,
			final Consumer<Object> configurationConsumer) {
		this.configuration = configuration;
		this.configurationConsumer = configurationConsumer;

		autoVBox = new AutoUiBuilder().build(configuration);
		autoVBox.populateView();
		title(messageSource.get(screen.name()))
				.content(autoVBox)
				.graphic(Icon.LINE_CHART)
				.actionButtonText(messageSource.get("Edit"))
				.cancelButtonText(messageSource.get("Cancel"));
	}

	@Override
	protected ScreenEditorModal getThis() {
		return this;
	}

	@Override
	protected void action() {
		autoVBox.populateModel();
		configurationConsumer.accept(configuration);
		hide();
	}
}
