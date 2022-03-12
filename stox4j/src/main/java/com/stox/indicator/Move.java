package com.stox.indicator;

import java.util.List;

import org.ta4j.core.Bar;

import lombok.Value;

@Value
public class Move {
	private final List<Bar> bars;
	private final Bar start, end;
	private final int startIndex, endIndex;
}