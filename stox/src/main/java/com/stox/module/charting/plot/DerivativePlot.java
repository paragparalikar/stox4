package com.stox.module.charting.plot;

import java.util.List;

import com.stox.module.charting.Configuration;
import com.stox.module.core.model.Bar;

public abstract class DerivativePlot<T> extends Plot<T> {

	public DerivativePlot(Configuration configuration) {
		super(configuration);
	}

	public abstract void load(final List<Bar> bars);

}
