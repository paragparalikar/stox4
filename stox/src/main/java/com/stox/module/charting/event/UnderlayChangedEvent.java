package com.stox.module.charting.event;

import com.stox.module.charting.plot.DerivativePlot;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class UnderlayChangedEvent extends Event {
	private static final long serialVersionUID = -4661085740139090637L;

	public static final EventType<UnderlayChangedEvent> TYPE = new EventType<>("UnderlayChangedEvent");

	private final DerivativePlot<?,?> plot;
	
	public UnderlayChangedEvent(@NonNull final DerivativePlot<?,?> plot) {
		super(TYPE);
		this.plot = plot;
	}

}
