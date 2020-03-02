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
public abstract class DerivativePlot<T, S extends DerivativePlotState> extends Plot<T, S> {
	
	private Underlay underlay = Underlay.NONE;
	
	public abstract void load(final List<Bar> bars);

	public DerivativePlot(Configuration configuration) {
		super(configuration);
	}

	@Override
	public void state(S state) {
		this.underlay = state.underlay();
	}
	
	@Override
	protected S fill(S state) {
		super.fill(state).underlay(underlay);
		return state;
	}

}
