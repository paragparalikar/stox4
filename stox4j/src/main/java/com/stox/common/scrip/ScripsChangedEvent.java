package com.stox.common.scrip;

import java.util.List;

import lombok.Value;

@Value
public class ScripsChangedEvent {

	private final List<Scrip> scrips;
	
}
