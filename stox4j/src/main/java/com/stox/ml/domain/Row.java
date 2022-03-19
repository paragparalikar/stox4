package com.stox.ml.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.Value;

@Value
public class Row {
	
	@NonNull private String label;
	@NonNull private List<Double> features;
	
	public String toCsv() {
		return String.join(",", features.stream()
				.map(Object::toString)
				.collect(Collectors.joining(",")), label);
	}
	
	public String getHeaderCsv() {
		final List<String> headers = new ArrayList<>();
		for(int index = 0; index < features.size(); index++) headers.add("V"+index);
		headers.add("class");
		return String.join(",", headers);
	}
	
}
