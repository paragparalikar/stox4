package com.stox.module.charting.drawing.segment;


import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SegmentMoveMouseEventHandler implements EventHandler<MouseEvent> {
	
	private double	x;
	private double	y;
	private final Segment segment;
	
	@Override
	public void handle(MouseEvent event) {
		if (MouseButton.PRIMARY.equals(event.getButton()) && !event.isConsumed()) {
			if (MouseEvent.MOUSE_PRESSED.equals(event.getEventType())) {
				x = event.getX();
				y = event.getY();
				event.consume();
			} else if (MouseEvent.MOUSE_DRAGGED.equals(event.getEventType())) {
				segment.move(event.getX() - x, event.getY() - y);
				x = event.getX();
				y = event.getY();
				event.consume();
				segment.getNode().fireEvent(new UpdatableRequestEvent(segment));
			}
		}
	}

}
