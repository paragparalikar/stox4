package com.stox.common.event;

import com.stox.common.scrip.Scrip;

import lombok.Data;

@Data
public class SelectedScripQueryEvent {

	private Scrip scrip;
	
}
