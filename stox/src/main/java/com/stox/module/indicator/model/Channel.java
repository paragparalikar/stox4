package com.stox.module.indicator.model;

import java.text.NumberFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(fluent = true)
public class Channel {
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
	
	static {
		NUMBER_FORMAT.setMaximumFractionDigits(2);
	}
	
	private double upper;
	private double middle;
	private double lower;

	@Override
	public String toString() {
		return NUMBER_FORMAT.format(upper) + ", " + NUMBER_FORMAT.format(middle) + ", " + NUMBER_FORMAT.format(lower);
	}
}