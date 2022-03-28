package com.stox.charting.drawing;

import java.util.Optional;
import java.util.function.Function;

import com.stox.charting.chart.Chart;
import com.stox.charting.handler.ModeMouseHandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DrawingModeMouseHandler implements ModeMouseHandler, EventHandler<MouseEvent> {

	private final Chart chart;
	private final Runnable completionCallback;
	private final Function<Chart, Drawing<?>> drawingBuilder;
	
	@Override
	public void addListeners() {
		removeListeners();
		chart.getModeMouseHandler().removeListeners();
		chart.getContentArea().addEventHandler(MouseEvent.ANY, this);
	}

	@Override
	public void removeListeners() {
		if(null != chart) chart.getContentArea().removeEventHandler(MouseEvent.ANY, this);
	}

	@Override
	public void handle(MouseEvent event) {
		if(MouseButton.PRIMARY.equals(event.getButton())) {
			if(MouseEvent.MOUSE_PRESSED.equals(event.getEventType())) {
				final Drawing<?> drawing = drawingBuilder.apply(chart);
				chart.add(drawing);
				drawing.moveTo(event.getX(), event.getY());
				chart.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
					if(!mouseEvent.isConsumed() &&  MouseButton.SECONDARY.equals(mouseEvent.getButton())) {
						chart.remove(drawing);
						mouseEvent.consume();
					}
				});
			} else if(MouseEvent.MOUSE_RELEASED.equals(event.getEventType())) {
				removeListeners();
				chart.getModeMouseHandler().addListeners();
				Optional.ofNullable(completionCallback).ifPresent(Runnable::run);
			}
		}
	}
}
