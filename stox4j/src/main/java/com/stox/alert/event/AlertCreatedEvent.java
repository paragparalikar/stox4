package com.stox.alert.event;

import com.stox.alert.Alert;

import lombok.Value;

@Value
public class AlertCreatedEvent {

	private final Alert alert;

}
