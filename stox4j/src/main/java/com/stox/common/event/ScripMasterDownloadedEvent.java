package com.stox.common.event;

import java.util.List;

import com.stox.common.scrip.Scrip;

import lombok.Value;

@Value
public class ScripMasterDownloadedEvent {

	private final List<Scrip> scrips;
	
}
