package com.stox.charting.axis.y;

import javafx.event.Event;
import javafx.event.EventType;

public class YAxisRedrawRequestEvent extends Event {
	private static final long serialVersionUID = -5262487131869260334L;

	public static final EventType<YAxisRedrawRequestEvent> TYPE = new EventType<>("YAxisRedrawRequestEvent");
	
	public YAxisRedrawRequestEvent() {
		super(TYPE);
	}
	
}
