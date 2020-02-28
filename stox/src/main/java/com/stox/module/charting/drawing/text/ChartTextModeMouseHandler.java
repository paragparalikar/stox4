package com.stox.module.charting.drawing.text;

import java.util.Optional;

import com.stox.module.charting.ChartingView;
import com.stox.module.charting.ModeMouseHandler;
import com.stox.module.charting.chart.Chart;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChartTextModeMouseHandler implements ModeMouseHandler, EventHandler<MouseEvent> {

	private final ChartingView chartingView;
	private final Runnable endCallback;

	@Override
	public void attach() {
		chartingView.getNode().addEventFilter(MouseEvent.MOUSE_PRESSED, this);
	}

	@Override
	public void detach() {
		chartingView.getNode().removeEventFilter(MouseEvent.MOUSE_PRESSED, this);
	}

	@Override
	public void handle(MouseEvent event) {
		final Chart chart = chartingView.chart(event.getScreenX(), event.getScreenY());
		if (null != chart) {
			chartingView.mouseModeHandler(null);
			final ChartText chartText = new ChartText();
			chart.add(chartText);
			final Parent parent = chartText.getNode().getParent();
			final Point2D endPoint = parent.screenToLocal(event.getScreenX(), event.getScreenY());
			chartText.move(endPoint.getX(), endPoint.getY());
			chartText.edit();
		}
		Optional.ofNullable(endCallback).ifPresent(Runnable::run);
	}

}
