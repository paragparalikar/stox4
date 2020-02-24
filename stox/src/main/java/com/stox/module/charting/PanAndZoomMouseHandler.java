package com.stox.module.charting;

import com.stox.module.charting.event.PanRequestEvent;
import com.stox.module.charting.event.ZoomRequestEvent;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PanAndZoomMouseHandler implements ModeMouseHandler {

	private double startX;

	@NonNull
	private final Node node;
	private final EventHandler<MouseEvent> pressedHandler = event -> press(event);
	private final EventHandler<MouseEvent> draggedHandler = event -> drag(event);
	private final EventHandler<ScrollEvent> scrollHandler = event -> scroll(event);

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

	private void scroll(final ScrollEvent event) {
		if (!event.isConsumed()) {
			event.consume();
			node.fireEvent(new ZoomRequestEvent(event.getX(), 0 < event.getDeltaY() ? -5 : 5));
		}
	}

	@Override
	public void attach() {
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, pressedHandler);
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED, draggedHandler);
		node.addEventHandler(ScrollEvent.SCROLL, scrollHandler);
	}

	@Override
	public void detach() {
		node.removeEventHandler(MouseEvent.MOUSE_PRESSED, pressedHandler);
		node.removeEventHandler(MouseEvent.MOUSE_DRAGGED, draggedHandler);
		node.removeEventHandler(ScrollEvent.SCROLL, scrollHandler);
	}

}
