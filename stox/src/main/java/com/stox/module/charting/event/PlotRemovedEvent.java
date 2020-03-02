package com.stox.module.charting.event;

import com.stox.module.charting.plot.DerivativePlot;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PlotRemovedEvent extends Event {
	private static final long serialVersionUID = 732857006798695256L;

	public static final EventType<PlotRemovedEvent> TYPE = new EventType<>("PlotRemovedEvent");

	private final DerivativePlot<?,?> plot;

	public PlotRemovedEvent(@NonNull final DerivativePlot<?,?> plot) {
		super(TYPE);
		this.plot = plot;
	}

}
