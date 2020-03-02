package com.stox.module.charting.plot;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.Configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class DerivativePlotState extends PlotState {
	public static final String TYPE = "derivative";
	
	@SerializedName("underlay")
	private Underlay underlay;
	
	public DerivativePlotState() {
		super(TYPE);
	}
	
	public DerivativePlotState(@NonNull final String type) {
		super(type);
	}

	@Override
	public Plot<?, ?> plot(Configuration configuration) {
		throw new UnsupportedOperationException();
	}
	
}
