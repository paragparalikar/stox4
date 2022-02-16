package com.stox.charting.handler;

import javafx.scene.Node;

public class CompositeModeMouseHandler implements ModeMouseHandler {

	private final ModeMouseHandler[] handlers;
	
	public CompositeModeMouseHandler(ModeMouseHandler...handlers) {
		this.handlers = handlers;
	}

	@Override
	public void attach(Node node) {
		if(null != handlers && 0 < handlers.length) {
			for(ModeMouseHandler handler : handlers) {
				handler.attach(node);
			}
		}
	}

	@Override
	public void detach() {
		if(null != handlers && 0 < handlers.length) {
			for(ModeMouseHandler handler : handlers) {
				handler.detach();
			}
		}
	}

}
