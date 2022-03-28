package com.stox.charting;

import java.time.ZonedDateTime;

import com.stox.common.scrip.Scrip;

import lombok.Value;

@Value
public class ChartingArguments {
	public static final ChartingArguments NULL = new ChartingArguments(null, null);
	
	private final Scrip scrip;
	private final ZonedDateTime to;
}