package com.stox.module.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@RequiredArgsConstructor
public enum Exchange {

	NSE("NSE", "National Stock Exchange"), 
	BSE("BSE", "Bombay Stock Exchange");

	@Getter
	private final String code;
	private final String name;

	@Override
	public String toString() {
		return name;
	}
	
	public String lastDownloadDateKey(){
		return code.toLowerCase()+".download.date.last";
	}
}
