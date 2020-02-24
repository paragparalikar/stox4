package com.stox.module.charting.event;

import com.stox.module.charting.axis.Updatable;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class UpdatableRequestEvent extends Event {
	private static final long serialVersionUID = -5092828484914700437L;

	public static final EventType<UpdatableRequestEvent> TYPE = new EventType<>("UpdatableRequestEvent");

	private final Updatable updatable;

	public UpdatableRequestEvent(@NonNull final Updatable updatable) {
		super(TYPE);
		this.updatable = updatable;
	}

}
