package com.stox.module.charting.plot.price;

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
public class PricePlot extends Plot<Bar> {
	
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
		return bar.high();
	}

	@Override
	public double min(Bar bar) {
		return bar.low();
	}

}
