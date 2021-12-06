package com.stox.module.indicator.model;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MultiValue {
private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
	
	static {
		NUMBER_FORMAT.setMaximumFractionDigits(2);
	}

	private double[] values;
	
	@Override
	public String toString() {
		return Arrays.stream(values)
				.mapToObj(NUMBER_FORMAT::format)
				.collect(Collectors.joining(","));
	}
	
}
