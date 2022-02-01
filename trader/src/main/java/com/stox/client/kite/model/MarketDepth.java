package com.stox.client.kite.model;

import java.util.List;

import lombok.Data;

@Data
public class MarketDepth {

	private List<Depth> buy;
	private List<Depth> sell;
	
}
