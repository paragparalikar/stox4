package com.stox.client.kite.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderType {

	MARKET, LIMIT, SL, 
	
	@JsonProperty("SL-M")
	SLM;
	
}
