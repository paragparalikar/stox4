package com.stox.common.event;

import com.stox.common.scrip.Scrip;

import lombok.Value;

@Value
public class ScripSelectedEvent {

	private final Scrip scrip;
	
}
