package com.stox.module.charting.plot.price;

import java.util.Optional;

import com.stox.module.charting.Configuration;
import com.stox.module.charting.plot.Plot;
import com.stox.module.charting.unit.Unit;
import com.stox.module.core.model.Bar;
import com.stox.module.core.model.Scrip;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class PricePlot<S extends PricePlotState> extends Plot<Bar, S> {
	
	private Scrip scrip;

	public PricePlot(final Configuration configuration) {
		super(configuration);
	}
	
	@Override
	public Unit<Bar> unit() {
		return null;
	}

	@Override
	public void showIndexInfo(int index) {

	}

	@Override
	public double max(Bar bar) {
		return bar.getHigh();
	}

	@Override
	public double min(Bar bar) {
		return bar.getLow();
	}
	
	@Override
	protected S fill(S state) {
		super.fill(state);
		Optional.ofNullable(scrip).ifPresent(value -> state.isin(scrip.getIsin()));
		return state;
	}

	@Override
	@SuppressWarnings("unchecked")
	public S state() {
		return fill((S) new PricePlotState());
	}

	@Override
	public void state(S state) {
		
	}

}
