package com.stox.module.core.model;

import com.stox.util.MathUtil;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Bar implements Comparable<Bar>{

	private double open;
	private double high;
	private double low;
	private double close;
	private double previousClose;
	private double volume;
	private long date;
	private String isin;
	
	@Override
	public int compareTo(final Bar o) {
		if (o.date > date) {
			return 1;
		} else if (o.date < date) {
			return -1;
		} else {
			return 0;
		}
	}

	public void apply(final double factor) {
		open = MathUtil.round(open / factor, 0.05);
		high = MathUtil.round(high / factor, 0.05);
		low = MathUtil.round(low / factor, 0.05);
		close = MathUtil.round(close / factor, 0.05);
		previousClose = MathUtil.round(previousClose / factor, 0.05);
		volume = Math.ceil(volume * factor);
	}
	
}
