package com.stox.charting.handler.zoom;

import com.stox.charting.handler.ModeMouseHandler;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;

public class ZoomModeMouseHandler implements ModeMouseHandler {

	private Node node;
	private final EventHandler<ScrollEvent> scrollHandler = event -> scroll(event);

	private void scroll(final ScrollEvent event) {
		if (!event.isConsumed()) {
			event.consume();
			node.fireEvent(new ZoomRequestEvent(event.getX(), 0 < event.getDeltaY() ? -5 : 5));
		}
	}
	
	@Override
	public void attach(Node node) {
		if(null != this.node) detach();
		this.node = node;
		node.addEventHandler(ScrollEvent.SCROLL, scrollHandler);
	}

	@Override
	public void detach() {
		if(null != node) {
			node.removeEventHandler(ScrollEvent.SCROLL, scrollHandler);
			node = null;
		}
	}

}
