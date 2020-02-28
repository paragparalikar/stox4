package com.stox.module.charting.chart;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.axis.vertical.MutableYAxisState;
import com.stox.module.charting.plot.DerivativePlotState;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ChartState {

	@SerializedName("mutableYAxisState")
	private MutableYAxisState mutableYAxisState;
	
	@SerializedName("derivativePlotStates")
	private Set<DerivativePlotState> derivativePlotStates = new HashSet<>();
	
}
