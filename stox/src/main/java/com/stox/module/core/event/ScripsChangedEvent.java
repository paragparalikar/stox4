package com.stox.module.core.event;

import java.util.List;

import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ScripsChangedEvent extends Event {
	private static final long serialVersionUID = -298502198033433994L;

	public static final EventType<ScripsChangedEvent> TYPE = new EventType<>("ScripsChangedEvent");

	private final Exchange exchange;
	private final List<Scrip> scrips;

	public ScripsChangedEvent(@NonNull final Exchange exchange, @NonNull final List<Scrip> scrips) {
		super(TYPE);
		this.scrips = scrips;
		this.exchange = exchange;
	}

}
