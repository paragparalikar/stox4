package com.stox.charting.plot.rule.facade;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.charting.plot.PlotFacade;
import com.stox.common.ui.ModelAndView;
import com.stox.indicator.RuleIndicator;
import com.stox.rule.BreakoutBarRule;

public class BreakoutBarRulePlotFacade implements PlotFacade<Boolean> {
	
	@Override
	public String toString() {
		return "Breakout Bar";
	}

	@Override
	public ModelAndView createConfigView() {
		return null;
	}

	@Override
	public Indicator<Boolean> createIndicator(BarSeries barSeries) {
		return new RuleIndicator(new BreakoutBarRule(barSeries), barSeries);
	}

	@Override
	public double resolveLowValue(Boolean model) {
		return 0;
	}

	@Override
	public double resolveHighValue(Boolean model) {
		return 0;
	}

}
