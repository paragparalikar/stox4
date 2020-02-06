package com.stox.core.model;

public enum Exchange {

	NSE("NSE", "National Stock Exchange"), 
	BSE("BSE", "Bombay Stock Exchange");

	private final String code;
	private final String name;

	private Exchange(final String code, final String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String getKeyLastDownloadDate(){
		return code.toLowerCase()+".download.date.last";
	}
}
