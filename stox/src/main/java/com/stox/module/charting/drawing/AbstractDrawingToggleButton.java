package com.stox.module.charting.drawing;

import java.util.Optional;

import com.stox.fx.fluent.scene.control.FluentToggleButton;
import com.stox.module.charting.ChartingView;
import com.stox.module.charting.chart.Chart;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public abstract class AbstractDrawingToggleButton extends FluentToggleButton implements ChangeListener<Boolean> {

	private final ChartingView chartingView;
	
	public AbstractDrawingToggleButton(ChartingView chartingView) {
		this.chartingView = chartingView;
		classes("drawing-chart-button", "small");
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if (!newValue && null != chartingView) {
			chartingView.mouseModeHandler(null);
		}
	}

	protected void add(@NonNull final Drawing<?> drawing, final double screenX, final double screenY) {
		final Chart chart = chartingView.chart(screenX, screenY);
		Optional.ofNullable(chart).ifPresent(c -> chart.add(drawing));
	}
	
	public ChartingView getChartingView() {
		return chartingView;
	}

}

