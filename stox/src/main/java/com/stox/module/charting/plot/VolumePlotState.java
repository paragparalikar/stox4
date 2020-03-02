package com.stox.module.charting.plot;

import com.stox.module.charting.Configuration;

public class VolumePlotState extends DerivativePlotState {
	public static final String TYPE = "volume";

	public VolumePlotState() {
		super(TYPE);
	}

	@Override
	public Plot<?, ?> plot(Configuration configuration) {
		return null;
	}

}
