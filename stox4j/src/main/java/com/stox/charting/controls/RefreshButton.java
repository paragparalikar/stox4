package com.stox.charting.controls;

import com.stox.charting.ChartingView;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.common.scrip.Scrip;
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
		final ObjectProperty<Scrip> scripProperty = context.getScripProperty();
		final Scrip scrip = scripProperty.get();
		if(null != scrip) {
			scripProperty.set(null);
			scripProperty.set(scrip);
		}
	}
}
