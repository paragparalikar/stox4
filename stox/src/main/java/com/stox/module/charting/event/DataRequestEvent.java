package com.stox.module.charting.event;

import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.core.model.BarSpan;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class DataRequestEvent extends Event {
	private static final long serialVersionUID = 1L;
	public static final EventType<DataRequestEvent> TYPE = new EventType<DataRequestEvent>("BarDataRequestEvent");

	private final long to;
	private final BarSpan barSpan;
	private final XAxis xAxis;

	public DataRequestEvent(long to, @NonNull  final BarSpan barSpan, @NonNull final XAxis xAxis) {
		super(TYPE);
		this.to = to;
		this.barSpan = barSpan;
		this.xAxis = xAxis;
	}
}
