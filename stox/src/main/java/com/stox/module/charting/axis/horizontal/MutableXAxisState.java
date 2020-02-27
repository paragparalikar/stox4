package com.stox.module.charting.axis.horizontal;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class MutableXAxisState {

	@SerializedName("unitWidth")
	private double unitWidth;
	
	@SerializedName("maxUnitWidth")
	private double maxUnitWidth;
	
	@SerializedName("minUnitWidth")
	private double minUnitWidth;
	
	@SerializedName("pivotX")
	private double pivotX;
	
	@SerializedName("width")
	private double width;
}
