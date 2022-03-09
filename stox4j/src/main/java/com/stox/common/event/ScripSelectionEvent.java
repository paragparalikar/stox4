package com.stox.common.event;

import com.stox.common.scrip.Scrip;

import lombok.Value;

@Value
public class ScripSelectionEvent {

	private final Scrip scrip;
	
}
