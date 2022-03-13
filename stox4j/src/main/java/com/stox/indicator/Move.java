package com.stox.indicator;

import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.num.Num;

import lombok.Value;

@Value
public class Move {
	private final List<Bar> bars;
	private final boolean up, down;
	private final int startIndex, endIndex;
	
	public boolean isEmpty() {
		return null == bars || bars.isEmpty();
	}
	
	public Num getStartPrice() {
		if(bars.isEmpty()) return null;
		final Bar bar = bars.get(0);
		return up ? bar.getLowPrice() : bar.getHighPrice();
	}
	
	public Num getEndPrice() {
		if(bars.isEmpty()) return null;
		final Bar bar = bars.get(bars.size() - 1);
		return down ? bar.getLowPrice() : bar.getHighPrice();
	}
	
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Up : " + up + ", Down : " + down + ",");
		builder.append("Start : " + startIndex + " - " + getStartPrice() + ",");
		builder.append("End : " + endIndex + " - " + getEndPrice());
		return builder.toString();
	}
}