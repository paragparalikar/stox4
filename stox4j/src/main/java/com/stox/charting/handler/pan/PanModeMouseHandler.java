package com.stox.charting.handler.pan;

import com.stox.charting.chart.Chart;
import com.stox.charting.handler.ModeMouseHandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PanModeMouseHandler implements ModeMouseHandler {
	
	private final Chart chart;
	private double startX;
	
	private final EventHandler<MouseEvent> pressedHandler = event -> press(event);
	private final EventHandler<MouseEvent> draggedHandler = event -> drag(event);

	private void press(final MouseEvent event) {
		if (!event.isConsumed() && MouseButton.PRIMARY.equals(event.getButton())) {
			startX = event.getX();
		}
	}

	private void drag(final MouseEvent event) {
		final double deltaX = event.getX() - startX;
		if (!event.isConsumed() && 0 != deltaX && MouseButton.PRIMARY.equals(event.getButton())) {
			chart.fireEvent(new PanRequestEvent(deltaX));
			startX = event.getX();
		}
	}
	
	@Override
	public void addListeners() {
		removeListeners();
		chart.getContentArea().addEventHandler(MouseEvent.MOUSE_PRESSED, pressedHandler);
		chart.getContentArea().addEventHandler(MouseEvent.MOUSE_DRAGGED, draggedHandler);
	}

	@Override
	public void removeListeners() {
		if(null != chart) {
			chart.getContentArea().removeEventHandler(MouseEvent.MOUSE_PRESSED, pressedHandler);
			chart.getContentArea().removeEventHandler(MouseEvent.MOUSE_DRAGGED, draggedHandler);
		}
	}

}
