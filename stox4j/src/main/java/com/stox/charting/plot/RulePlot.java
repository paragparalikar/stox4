package com.stox.charting.plot;

import org.ta4j.core.Rule;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.unit.BooleanUnit;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.RuleIndicator;

public class RulePlot extends Plot<Boolean> {

	private final Rule rule;
	
	public RulePlot(Rule rule, ChartingContext context) {
		super(context, () -> new BooleanUnit(context), null);
		this.rule = rule;
	}

	@Override
	public boolean reload(Scrip scrip, XAxis xAxis) {
		setIndicator(new RuleIndicator(rule, getContext().getBarSeries()));
		return true;
	}
	
	@Override
	public void updateYAxis(int startIndex, int endIndex, YAxis yAxis) {

	}

}
