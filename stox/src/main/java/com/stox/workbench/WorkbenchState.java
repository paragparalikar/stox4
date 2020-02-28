package com.stox.workbench;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class WorkbenchState {

	@SerializedName("x")
	private double x;
	
	@SerializedName("y")
	private double y;
	
	@SerializedName("width")
	private double width;
	
	@SerializedName("height")
	private double height;

	@SerializedName("maximized")
	private boolean maximized;
	
}
