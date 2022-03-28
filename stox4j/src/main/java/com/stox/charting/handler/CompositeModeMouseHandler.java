package com.stox.charting.handler;

public class CompositeModeMouseHandler implements ModeMouseHandler {

	private final ModeMouseHandler[] handlers;
	
	public CompositeModeMouseHandler(ModeMouseHandler...handlers) {
		this.handlers = handlers;
	}

	@Override
	public void addListeners() {
		removeListeners();
		if(null != handlers && 0 < handlers.length) {
			for(ModeMouseHandler handler : handlers) {
				handler.addListeners();
			}
		}
	}

	@Override
	public void removeListeners() {
		if(null != handlers && 0 < handlers.length) {
			for(ModeMouseHandler handler : handlers) {
				handler.removeListeners();
			}
		}
	}

}
