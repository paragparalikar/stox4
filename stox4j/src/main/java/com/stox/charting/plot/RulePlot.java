package com.stox.charting.plot;

import org.ta4j.core.Rule;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.unit.BooleanUnit;
import com.stox.indicator.RuleIndicator;

public class RulePlot extends Plot<Boolean> {

	private final Rule rule;
	
	public RulePlot(Rule rule, ChartingContext context) {
		super(context, () -> new BooleanUnit(context));
		this.rule = rule;
	}

	@Override
	public boolean load(XAxis xAxis) {
		setIndicator(new RuleIndicator(rule, getContext().getBarSeries()));
		return true;
	}
	
	@Override
	public void updateYAxis(int startIndex, int endIndex, YAxis yAxis) {
	}

	@Override
	protected double resolveLowValue(Boolean model) {
		return 0;
	}

	@Override
	protected double resolveHighValue(Boolean model) {
		return 0;
	}

}
