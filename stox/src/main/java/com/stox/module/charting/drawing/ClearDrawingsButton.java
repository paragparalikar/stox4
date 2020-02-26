package com.stox.module.charting.drawing;

import com.stox.fx.widget.Icon;
import com.stox.module.charting.ChartingView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class ClearDrawingsButton extends Button implements EventHandler<ActionEvent> {
	
	private final ChartingView chartingView;

	public ClearDrawingsButton(ChartingView chartingView) {
		super(Icon.ERASER);
		this.chartingView = chartingView;
		getStyleClass().addAll("icon", "primary");
		setTooltip(new Tooltip("Clear All Drawings"));
		addEventHandler(ActionEvent.ACTION, this);
	}

	@Override
	public void handle(ActionEvent event) {
		chartingView.clearDrawings();
	}

}
