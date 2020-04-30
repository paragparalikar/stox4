package com.stox.module.charting.indicator;

import com.stox.fx.fluent.scene.control.FluentListView;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.charting.ChartingView;
import com.stox.workbench.modal.ActionModal;

import lombok.NonNull;

public class IndicatorChooserModal extends ActionModal<IndicatorChooserModal>{

	private final ChartingView chartingView;
	private final FxMessageSource messageSource;
	private final FluentListView<ChartIndicator<?,?,?>> listView = new FluentListView<ChartIndicator<?,?,?>>();

	public IndicatorChooserModal(
			@NonNull final ChartingView chartingView,
			@NonNull final FxMessageSource messageSource) {
		this.chartingView = chartingView;
		this.messageSource = messageSource;
		title(messageSource.get("Indicators")).content(listView).graphic(Icon.LINE_CHART);
		listView.getItems().addAll(ChartIndicator.ALL);
		actionButtonText(messageSource.get("Add"));
		cancelButtonText(messageSource.get("Cancel"));
	}

	@Override
	protected void action() {
		final ChartIndicator<?,?,?> indicator = listView.selectionModel().getSelectedItem();
		if(null != indicator){
			final IndicatorPlot<?,?,?> plot = new IndicatorPlot<>(messageSource, chartingView.configuration(), indicator);
			chartingView.load(plot);
			chartingView.add(plot);
			hide();
		}
	}

	@Override
	protected IndicatorChooserModal getThis() {
		return this;
	}
	
}
