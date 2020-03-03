package com.stox.module.charting.drawing;

import com.stox.module.charting.drawing.event.DrawingRemoveRequestEvent;
import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class AbstractDrawing<S extends DrawingState> implements Drawing<S> {

	private double x, y;

	protected void bind() {
		getNode().addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
		getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
	}

	private void onMousePressed(final MouseEvent event) {
		if (!event.isConsumed()) {
			event.consume();
			if (MouseButton.SECONDARY.equals(event.getButton())) {
				getNode().fireEvent(new DrawingRemoveRequestEvent(this));
			} else {
				x = event.getX();
				y = event.getY();
			}
		}
	}

	private void onMouseDragged(final MouseEvent event) {
		if (MouseButton.PRIMARY.equals(event.getButton()) && !event.isConsumed()) {
			move(event.getX() - x, event.getY() - y);
			x = event.getX();
			y = event.getY();
			event.consume();
			getNode().fireEvent(new UpdatableRequestEvent(this));
		}
	}

}
