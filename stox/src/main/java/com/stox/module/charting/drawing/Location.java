package com.stox.module.charting.drawing;

import java.util.Optional;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Location {

	@SerializedName("date")
	private long date;

	@SerializedName("value")
	private double value;
	
	public void state(final Location location) {
		Optional.ofNullable(location).ifPresent(l -> {
			date(location.date());
			value(location.value());
		});
	}

}
