package com.stox.charting.event;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class DataRequestEvent extends Event {
	private static final long serialVersionUID = 1L;
	public static final EventType<DataRequestEvent> TYPE = new EventType<DataRequestEvent>("BarDataRequestEvent");

	public DataRequestEvent() {
		super(TYPE);
	}
}
