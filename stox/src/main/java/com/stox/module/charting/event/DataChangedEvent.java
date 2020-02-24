package com.stox.module.charting.event;

import java.util.List;

import com.stox.module.core.model.Bar;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class DataChangedEvent extends Event {
	private static final long serialVersionUID = 8748545028485578771L;
	public static final EventType<DataChangedEvent> TYPE = new EventType<>("BarDataChangedEvent");

	private final List<Bar> bars;

	public DataChangedEvent(@NonNull final List<Bar> bars) {
		super(TYPE);
		this.bars = bars;
	}

}
