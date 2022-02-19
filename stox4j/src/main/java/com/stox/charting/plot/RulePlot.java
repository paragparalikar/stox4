package com.stox.charting.plot;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.charting.chart.PlotInfo;
import com.stox.charting.unit.BooleanUnit;
import com.stox.indicator.RuleIndicator;

public class RulePlot extends Plot<Boolean> {

	private final Rule rule;
	
	public RulePlot(Rule rule) {
		super(BooleanUnit::new);
		this.rule = rule;
	}

	@Override
	public void reload() {
		final BarSeries barSeries = getContext().getBarSeriesProperty().get();
		if(null != barSeries) setIndicator(new RuleIndicator(rule, barSeries));
	}
	
	@Override public void updateYAxis(int startIndex, int endIndex) {}
	@Override protected double resolveLowValue(Boolean model) {return 0;}
	@Override protected double resolveHighValue(Boolean model) {return 0;}

	@Override
	public PlotInfo<Boolean> getInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
