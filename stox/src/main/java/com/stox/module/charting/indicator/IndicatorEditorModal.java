package com.stox.module.charting.indicator;

import java.util.function.Consumer;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.auto.AutoUiBuilder;
import com.stox.fx.widget.auto.AutoVBox;
import com.stox.workbench.modal.ActionModal;

public class IndicatorEditorModal extends ActionModal<IndicatorEditorModal>{

	private final AutoVBox autoVBox;
	private final Object configuration;
	private final Consumer<Object> configurationConsumer;

	@SuppressWarnings("rawtypes")
	public IndicatorEditorModal(
			final FxMessageSource messageSource,
			final Object configuration, 
			final ChartIndicator chartIndicator,
			final Consumer<Object> configurationConsumer) {
		this.configuration = configuration;
		this.configurationConsumer = configurationConsumer;

		autoVBox = new AutoUiBuilder().build(configuration);
		autoVBox.populateView();
		title(messageSource.get(chartIndicator.name()))
			.content(autoVBox)
			.graphic(Icon.LINE_CHART)
			.actionButtonText(messageSource.get("Edit"))
			.cancelButtonText(messageSource.get("Cancel"));
	}

	@Override
	protected IndicatorEditorModal getThis() {
		return this;
	}

	@Override
	protected void action() {
		autoVBox.populateModel();
		configurationConsumer.accept(configuration);
		hide();
	}
	
}
