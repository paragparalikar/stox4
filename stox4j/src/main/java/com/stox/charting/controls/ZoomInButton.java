package com.stox.charting.controls;

import com.stox.charting.ChartingView;
import com.stox.common.ui.Icon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ZoomInButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingView chartingView;
	
	public ZoomInButton(ChartingView chartingView) {
		super(Icon.PLUS);
		setOnAction(this);
		this.chartingView = chartingView;
		getStyleClass().addAll("icon", "charting-control-button");
	}

	@Override
	public void handle(ActionEvent event) {
		final int deltaX = 10;
		final double x = chartingView.getPriceChart().getContentArea().getWidth() / 2;
		chartingView.getXAxis().zoom(x, deltaX);
		chartingView.redraw();
	}

}
