package com.stox.ml.domain;

import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.Value;
import weka.core.DenseInstance;
import weka.core.Instance;

@Value
public class Row {
	
	private String label;
	@NonNull private List<Double> features;
	
	public Instance toInstance() {
		return new DenseInstance(1, this.features.stream()
				.mapToDouble(Double::doubleValue)
				.toArray());
	}
	
	public String toCsv() {
		return String.join(",", features.stream()
				.map(Object::toString)
				.collect(Collectors.joining(",")), label);
	}
	
}
