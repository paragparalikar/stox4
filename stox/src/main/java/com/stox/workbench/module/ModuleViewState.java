package com.stox.workbench.module;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ModuleViewState{

	@SerializedName("x")
	private double x;
	
	@SerializedName("y")
	private double y;
	
	@SerializedName("width")
	private double width;
	
	@SerializedName("height")
	private double height;
	
	@SerializedName("state")
	private String state;

}
