package com.stox.module.charting.plot.price;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.plot.PlotState;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class PricePlotState extends PlotState{

	@SerializedName("isin")
	private String isin;

}
