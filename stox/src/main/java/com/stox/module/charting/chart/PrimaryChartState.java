package com.stox.module.charting.chart;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.plot.price.PrimaryPricePlotState;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class PrimaryChartState extends ChartState {

	@SerializedName("primaryPricePlotState")
	private PrimaryPricePlotState primaryPricePlotState;

}
