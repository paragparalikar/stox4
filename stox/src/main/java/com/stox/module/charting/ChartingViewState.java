package com.stox.module.charting;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.axis.horizontal.MutableXAxisState;
import com.stox.module.charting.chart.PrimaryChartState;
import com.stox.module.charting.chart.SecondaryChartState;
import com.stox.module.core.model.BarSpan;
import com.stox.workbench.module.ModuleViewState;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class ChartingViewState extends ModuleViewState {
	
	@SerializedName("to")
	private long to;
	
	@SerializedName("barSpan")
	private BarSpan barSpan;
	
	@SerializedName("mutableXAxisState")
	private MutableXAxisState mutableXAxisState;
	
	@SerializedName("primaryChartState")
	private PrimaryChartState primaryChartState;
	
	@SerializedName("secondaryChartStates")
	private Set<SecondaryChartState> secondaryChartStates = new HashSet<>();

}
