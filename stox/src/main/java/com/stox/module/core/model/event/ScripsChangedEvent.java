package com.stox.module.core.model.event;

import java.util.List;

import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;

import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class ScripsChangedEvent {

	private final Exchange exchange;
	
	private final List<Scrip> scrips;

}
