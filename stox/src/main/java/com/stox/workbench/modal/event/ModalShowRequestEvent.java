package com.stox.workbench.modal.event;

import com.stox.workbench.modal.Modal;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ModalShowRequestEvent extends Event {
	private static final long serialVersionUID = -8045726051178765L;
	
	public static final EventType<ModalShowRequestEvent> TYPE = new EventType<>("ModalShowRequestEvent");

	private final Modal<?> modal;
	
	public ModalShowRequestEvent(@NonNull final Modal<?> modal) {
		super(TYPE);
		this.modal = modal;
	}

}
