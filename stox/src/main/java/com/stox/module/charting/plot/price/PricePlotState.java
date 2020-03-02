package com.stox.module.charting.plot.price;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.plot.Plot;
import com.stox.module.charting.plot.PlotState;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class PricePlotState extends PlotState{
	public static final String TYPE = "price";

	@SerializedName("isin")
	private String isin;

	public PricePlotState() {
		this(TYPE);
	}
	
	public PricePlotState(@NonNull final String type) {
		super(type);
	}
	
	@Override
	public Plot<?,?> plot(Configuration configuration) {
		return new PricePlot<>(configuration);
	}
	
}
