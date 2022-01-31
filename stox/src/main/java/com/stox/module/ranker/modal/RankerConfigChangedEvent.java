package com.stox.module.ranker.modal;

import javafx.event.Event;
import javafx.event.EventType;

public class RankerConfigChangedEvent extends Event {
	private static final long serialVersionUID = 567754499865191925L;

	public static final EventType<RankerConfigChangedEvent> TYPE = new EventType<>("rankerConfigChangedEvent");
	
	public RankerConfigChangedEvent() {
		super(TYPE);
	}

}
