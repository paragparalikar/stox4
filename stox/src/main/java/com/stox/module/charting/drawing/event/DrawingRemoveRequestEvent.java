package com.stox.module.charting.drawing.event;

import com.stox.module.charting.drawing.Drawing;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class DrawingRemoveRequestEvent extends Event {
	private static final long serialVersionUID = 5936048101642260691L;

	public static final EventType<DrawingRemoveRequestEvent> TYPE = new EventType<>("DrawingRemoveRequestEvent");
	
	private final Drawing<?> drawing;
	
	public DrawingRemoveRequestEvent(final Drawing<?> drawing) {
		super(TYPE);
		this.drawing = drawing;
	}

}
