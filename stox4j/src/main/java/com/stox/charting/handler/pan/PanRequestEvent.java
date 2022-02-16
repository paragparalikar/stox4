package com.stox.charting.handler.pan;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

@Getter
public class PanRequestEvent extends Event {
	private static final long serialVersionUID = 4640550328754051770L;

	public static final EventType<PanRequestEvent> TYPE = new EventType<>("PanRequestEvent");

	private final double deltaX;

	public PanRequestEvent(final double deltaX) {
		super(TYPE);
		this.deltaX = deltaX;
	}

}
