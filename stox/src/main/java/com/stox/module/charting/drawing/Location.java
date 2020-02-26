package com.stox.module.charting.drawing;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Location {

	@SerializedName("date")
	private long date;

	@SerializedName("value")
	private double value;

}
