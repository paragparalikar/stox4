package com.stox.module.charting.plot;

import com.stox.module.charting.Configuration;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@RequiredArgsConstructor
@Accessors(fluent = true)
public abstract class PlotState {
	
	public abstract Plot<?,?> plot(Configuration configuration);

	@NonNull
	private final String type;
	
}
