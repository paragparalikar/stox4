package com.stox.client.kite.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderVariety {

	@JsonProperty("regular")
	REGULAR, 
	
	@JsonProperty("amo")
	AMO, 
	
	@JsonProperty("bo")
	BO, 
	
	@JsonProperty("co")
	CO;
	
}
