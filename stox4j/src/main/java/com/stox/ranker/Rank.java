package com.stox.ranker;

import org.ta4j.core.num.Num;

import com.stox.common.scrip.Scrip;

import lombok.Value;

@Value
public class Rank {

	private Num value;
	private Scrip scrip;
	
	public String toString() {
		return scrip.getName() + " (" + String.format("%.4f", value.doubleValue()) + ")";
	}
}
