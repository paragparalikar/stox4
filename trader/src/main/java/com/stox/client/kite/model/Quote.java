package com.stox.client.kite.model;

import lombok.Data;

@Data
public class Quote {

	private int instrumentToken;
	private String timestamp;
	private String lastTradeTime;
	private double lastPrice;
	private int lastQuantity;
	private int buyQuantity;
	private int sellQuantity;
	private long volume;
	private double averagePrice;
	private double oi;
	private double oiDayHigh;
	private double oiDayLow;
	private Ohlc ohlc;
	private double netChange;
	private double lowerCircuitLimit;
	private double upperCircuitLimit;
	private MarketDepth depth;
	
}
