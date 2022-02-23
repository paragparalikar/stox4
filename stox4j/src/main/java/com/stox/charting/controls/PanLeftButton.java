package com.stox.charting.controls;

import com.stox.charting.ChartingView;
import com.stox.common.ui.Icon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class PanLeftButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingView chartingView;
	
	public PanLeftButton(ChartingView chartingView) {
		super(Icon.CHEVRON_LEFT);
		setOnAction(this);
		this.chartingView = chartingView;
		getStyleClass().addAll("icon", "charting-control-button");
	}

	@Override
	public void handle(ActionEvent event) {
		chartingView.getXAxis().pan(10);
		chartingView.redraw();
	}
}
