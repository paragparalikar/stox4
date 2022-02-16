package com.stox.charting.handler.pan;

import com.stox.charting.handler.ModeMouseHandler;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;

public class PanModeMouseHandler implements ModeMouseHandler {
	
	private Node node;
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
			event.consume();
			node.fireEvent(new PanRequestEvent(deltaX));
			startX = event.getX();
		}
	}
	
	@Override
	public void attach(@NonNull Node node) {
		if(null != this.node) detach();
		this.node = node;
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, pressedHandler);
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED, draggedHandler);
	}

	@Override
	public void detach() {
		if(null != node) {
			node.removeEventHandler(MouseEvent.MOUSE_PRESSED, pressedHandler);
			node.removeEventHandler(MouseEvent.MOUSE_DRAGGED, draggedHandler);
			node = null;
		}
	}

}
