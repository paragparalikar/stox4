package com.stox.charting.drawing;

import java.util.Optional;
import java.util.function.Function;

import com.stox.charting.chart.Chart;
import com.stox.charting.handler.ModeMouseHandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DrawingModeMouseHandler implements ModeMouseHandler {

	private final Chart chart;
	private final Runnable completionCallback;
	private final Function<Chart, Drawing<?>> drawingBuilder;
	private final EventHandler<MouseEvent> mousePressedHandler = this::onMousePressed;
	private final EventHandler<MouseEvent> mouseDraggedHandler = this::onMouseDragged;
	private final EventHandler<MouseEvent> mouseReleasedHandler = this::onMouseReleased;
	
	private Drawing<?> drawing;
	
	@Override
	public void addListeners() {
		removeListeners();
		if(null != chart) {
			chart.getModeMouseHandler().removeListeners();
			chart.getContentArea().addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
			chart.getContentArea().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedHandler);
			chart.getContentArea().addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedHandler);
		}
	}

	@Override
	public void removeListeners() {
		if(null != chart) {
			chart.getContentArea().removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
			chart.getContentArea().removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedHandler);
			chart.getContentArea().removeEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedHandler);
		}
	}

	private void onMousePressed(MouseEvent event) {
		chart.add(this.drawing = drawingBuilder.apply(chart));
		drawing.moveTo(event.getX(), event.getY());
	}
	
	private void onMouseDragged(MouseEvent event) {
		if(null != drawing) drawing.dragTo(event.getX(), event.getY());
	}
	
	private void onMouseReleased(MouseEvent event) {
		removeListeners();
		chart.getModeMouseHandler().addListeners();
		Optional.ofNullable(completionCallback).ifPresent(Runnable::run);
		drawing = null;
	}
}
