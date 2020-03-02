package com.stox.module.charting.event;

import com.stox.module.charting.plot.DerivativePlot;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ConfigChangedEvent extends Event {
	private static final long serialVersionUID = 8908887112476536911L;

	public static final EventType<ConfigChangedEvent> TYPE = new EventType<>("ConfigChangedEvent");
	
	private final DerivativePlot<?> plot;
	
	public ConfigChangedEvent(@NonNull final DerivativePlot<?> plot) {
		super(TYPE);
		this.plot = plot;
	}
	
}
