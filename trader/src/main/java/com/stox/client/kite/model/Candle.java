package com.stox.client.kite.model;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Candle {

	private final int volume;
	private final ZonedDateTime timestamp;
	private final double open, high, low, close;
	
}
