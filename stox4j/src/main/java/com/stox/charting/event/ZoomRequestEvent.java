package com.stox.charting.event;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ZoomRequestEvent extends Event {
	private static final long serialVersionUID = 170897449076562541L;

	public static final EventType<ZoomRequestEvent> TYPE = new EventType<>("ZoomRequestEvent");
	
	private final double x;
	private final int percentage;
	
	public ZoomRequestEvent(final double x, final int percentage) {
		super(TYPE);
		this.x = x;
		this.percentage = percentage;
	}

}
