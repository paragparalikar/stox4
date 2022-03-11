package com.stox.charting;

import java.time.ZonedDateTime;

import com.stox.common.scrip.Scrip;

import lombok.Value;

@Value
public class ChartingInput{
	public static final ChartingInput NULL = new ChartingInput(null, null);
	
	private final Scrip scrip;
	private final ZonedDateTime to;
}