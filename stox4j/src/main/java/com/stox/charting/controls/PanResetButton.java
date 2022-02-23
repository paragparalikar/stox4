package com.stox.charting.controls;

import com.stox.charting.ChartingView;
import com.stox.common.ui.Icon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class PanResetButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingView chartingView;

	public PanResetButton(ChartingView chartingView) {
		super(Icon.ANGLE_DOUBLE_RIGHT);
		setOnAction(this);
		this.chartingView = chartingView;
		getStyleClass().addAll("icon", "charting-control-button");
	}

	@Override
	public void handle(ActionEvent event) {
		chartingView.getXAxis().reset();
		chartingView.getXAxis().shift(chartingView.getContext().getBarCount());
		chartingView.redraw();
	}
}
