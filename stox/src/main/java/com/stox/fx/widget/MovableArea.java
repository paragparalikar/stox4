package com.stox.fx.widget;

import com.stox.fx.fluent.Area;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

public interface MovableArea<A extends MovableArea<A>> extends Area<A> {

	default A knob(Node knob) {
		final EventHandler<MouseEvent> eventHandler = new MovableMouseEventHandler(getThis());
		knob.addEventHandler(MouseEvent.MOUSE_PRESSED, eventHandler);
		knob.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandler);
		knob.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		return getThis();
	}
	
	@RequiredArgsConstructor
	class MovableMouseEventHandler implements EventHandler<MouseEvent>{

		private double offsetX;
		private double offsetY;
		private final Area<?> area;
		private boolean relocationMode;
		
		@Override
		public void handle(MouseEvent event) {
			final double x = event.getScreenX();
			final double y = event.getScreenY();
			final EventType<? extends MouseEvent> type = event.getEventType();
			if (MouseEvent.MOUSE_PRESSED.equals(type)) {
				offsetX = x;
				offsetY = y;
				relocationMode = true;
			} else if (relocationMode && MouseEvent.MOUSE_DRAGGED.equals(type)) {
				area.bounds(area.x() + x - offsetX, area.y() + y - offsetY, area.width(), area.height());
				offsetX = x;
				offsetY = y;
			} else if (MouseEvent.MOUSE_RELEASED.equals(type)) {
				offsetX = 0;
				offsetY = 0;
				relocationMode = false;
			}
		}

	}
	
}
