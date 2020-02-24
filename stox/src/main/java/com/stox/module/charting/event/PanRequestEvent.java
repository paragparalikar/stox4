package com.stox.module.charting.event;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PanRequestEvent extends Event {
	private static final long serialVersionUID = 4640550328754051770L;

	public static final EventType<PanRequestEvent> TYPE = new EventType<>("PanRequestEvent");

	private final double deltaX;

	public PanRequestEvent(final double deltaX) {
		super(TYPE);
		this.deltaX = deltaX;
	}

}
