package com.stox.module.charting.screen;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.charting.ChartingView;
import com.stox.module.screen.Screen;
import com.stox.module.screen.ScreenListView;
import com.stox.workbench.modal.ActionModal;

import lombok.NonNull;

public class ScreenChooserModal extends ActionModal<ScreenChooserModal> {
	
	private final ChartingView chartingView;
	private final FxMessageSource messageSource;
	private final ScreenListView listView = new ScreenListView();

	public ScreenChooserModal(
			@NonNull final ChartingView chartingView,
			@NonNull final FxMessageSource messageSource) {
		this.chartingView = chartingView;
		this.messageSource = messageSource;
		title(messageSource.get("Screens")).content(listView).graphic(Icon.FILTER);
		actionButtonText(messageSource.get("Add"));
		cancelButtonText(messageSource.get("Cancel"));
	}

	@Override
	protected void action() {
		final Screen<?> screen = listView.selectionModel().getSelectedItem();
		if(null != screen){
			final ScreenPlot<?> plot = new ScreenPlot<>(screen, messageSource, chartingView.configuration());
			chartingView.load(plot);
			chartingView.add(plot);
			hide();
		}
	}

	@Override
	protected ScreenChooserModal getThis() {
		return this;
	}

}
