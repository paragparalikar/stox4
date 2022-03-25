package com.stox.charting.controls;

import com.stox.charting.ChartingView;
import com.stox.charting.handler.zoom.ZoomRequestEvent;
import com.stox.common.ui.Icon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ZoomOutButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingView chartingView;
	
	public ZoomOutButton(ChartingView chartingView) {
		super(Icon.MINUS);
		setOnAction(this);
		this.chartingView = chartingView;
		getStyleClass().addAll("icon", "charting-control-button");
	}

	@Override
	public void handle(ActionEvent event) {
		final int deltaX = -10;
		final double x = chartingView.getPriceChart().getContentArea().getWidth() / 2;
		chartingView.zoom(new ZoomRequestEvent(x, deltaX));
	}

}
