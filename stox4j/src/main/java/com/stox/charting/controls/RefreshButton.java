package com.stox.charting.controls;

import com.stox.charting.ChartingContext;
import com.stox.charting.ChartingArguments;
import com.stox.charting.ChartingView;
import com.stox.common.ui.Icon;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class RefreshButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingView chartingView;
	
	public RefreshButton(ChartingView chartingView) {
		super(Icon.REFRESH);
		setOnAction(this);
		this.chartingView = chartingView;
		getStyleClass().addAll("icon", "charting-control-button");
	}

	@Override
	public void handle(ActionEvent event) {
		final ChartingContext context = chartingView.getContext();
		final ObjectProperty<ChartingArguments> inputProperty = context.getArgumentsProperty();
		final ChartingArguments input = inputProperty.get();
		inputProperty.set(ChartingArguments.NULL);
		inputProperty.set(input);
	}
}
