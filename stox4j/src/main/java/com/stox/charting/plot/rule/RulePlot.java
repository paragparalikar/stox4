package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.helpers.ConstantIndicator;

import com.stox.charting.plot.Plot;
import com.stox.charting.plot.PlotInfo;
import com.stox.charting.plot.Plottable;
import com.stox.charting.unit.BooleanUnit;

public class RulePlot extends Plot<Boolean> {

	private final Plottable<Boolean, ?> facade;
	private final RulePlotInfo plotInfo = new RulePlotInfo(this);
	
	public RulePlot(Plottable<Boolean, ?> facade) {
		super(BooleanUnit::new);
		this.facade = facade;
	}

	@Override
	public void reload() {
		final BarSeries barSeries = getContext().getBarSeriesProperty().get();
		if(null != barSeries) {
			plotInfo.setName(facade.toString());
			setIndicator(facade.createIndicator(barSeries));
		} else {
			plotInfo.setName(null);
			setIndicator(new ConstantIndicator<>(null, false));
		}
	}
	
	@Override public void updateYAxis(int startIndex, int endIndex) {}
	@Override protected double resolveLowValue(Boolean model) {return 0;}
	@Override protected double resolveHighValue(Boolean model) {return 0;}

	@Override
	public PlotInfo<Boolean> getInfo() {
		return plotInfo;
	}
}
