package com.stox.module.charting.drawing;

import com.stox.module.charting.drawing.event.DrawingRemoveRequestEvent;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class AbstractDrawing implements Drawing {

	protected void bind() {
		getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onMousePressed(event));
	}

	private void onMousePressed(final MouseEvent event) {
		if (MouseButton.SECONDARY.equals(event.getButton()) && !event.isConsumed()) {
			event.consume();
			getNode().fireEvent(new DrawingRemoveRequestEvent(this));
		}
	}

}
