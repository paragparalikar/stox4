package com.stox.module.charting.drawing;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.ChartingView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import lombok.NonNull;

public class ClearDrawingsButton extends Button implements EventHandler<ActionEvent> {
	
	private final ChartingView chartingView;

	public ClearDrawingsButton(@NonNull final FxMessageSource messageSource, @NonNull final ChartingView chartingView) {
		super(Icon.ERASER);
		this.chartingView = chartingView;
		getStyleClass().addAll("icon", "primary");
		setTooltip(Ui.tooltip(messageSource.get("Clear all drawings")));
		addEventHandler(ActionEvent.ACTION, this);
	}

	@Override
	public void handle(ActionEvent event) {
		chartingView.clearDrawings();
	}

}
