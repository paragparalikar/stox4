package com.stox.core.model;

import lombok.Data;

@Data
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
	
}
