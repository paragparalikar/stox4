package com.stox.module.charting.chart;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.axis.vertical.MutableYAxisState;
import com.stox.module.charting.plot.DerivativePlotState;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@RequiredArgsConstructor
@Accessors(fluent = true)
public class ChartState {
	
	@NonNull
	private final String type;

	@SerializedName("mutableYAxisState")
	private MutableYAxisState mutableYAxisState;
	
	@SerializedName("derivativePlotStates")
	private Set<DerivativePlotState> derivativePlotStates = new HashSet<>();
	
}
