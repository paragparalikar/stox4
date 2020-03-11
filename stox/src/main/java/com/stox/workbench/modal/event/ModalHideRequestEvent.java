package com.stox.workbench.modal.event;

import com.stox.workbench.modal.Modal;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ModalHideRequestEvent extends Event {
	private static final long serialVersionUID = -8045726051178765L;
	
	public static final EventType<ModalHideRequestEvent> TYPE = new EventType<>("ModalHideRequestEvent");

	private final Modal<?> modal;
	
	public ModalHideRequestEvent(@NonNull final Modal<?> modal) {
		super(TYPE);
		this.modal = modal;
	}

}
