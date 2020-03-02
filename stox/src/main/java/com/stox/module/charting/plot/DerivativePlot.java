package com.stox.module.charting.plot;

import java.util.List;

import com.stox.module.charting.Configuration;
import com.stox.module.core.model.Bar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public abstract class DerivativePlot<T> extends Plot<T> {
	
	private Underlay underlay = Underlay.NONE;
	
	public abstract void load(final List<Bar> bars);

	public DerivativePlot(Configuration configuration) {
		super(configuration);
	}

}
