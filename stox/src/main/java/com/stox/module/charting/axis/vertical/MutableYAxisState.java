package com.stox.module.charting.axis.vertical;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class MutableYAxisState {

	@SerializedName("semilog")
	private boolean semilog;
	
	@SerializedName("top")
	private double top;
	
	@SerializedName("bottom")
	private double bottom;
	
	@SerializedName("height")
	private double height;
	
	@SerializedName("min")
	private double min;
	
	@SerializedName("max")
	private double max;

}
