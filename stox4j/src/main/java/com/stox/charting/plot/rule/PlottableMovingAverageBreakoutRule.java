package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.indicator.RuleIndicator;
import com.stox.rule.MovingAverageBreakoutRule;
import com.stox.rule.MovingAverageBreakoutRule.MovingAverageBreakoutRuleConfig;

public class PlottableMovingAverageBreakoutRule extends AbstractPlottableRule<MovingAverageBreakoutRuleConfig> {

	@Override
	public String toString() {
		return "Moving Average Breakout";
	}
	
	
	@Override
	public MovingAverageBreakoutRuleConfig createConfig() {
		return new MovingAverageBreakoutRuleConfig();
	}

	@Override
	public Indicator<Boolean> createIndicator(MovingAverageBreakoutRuleConfig config, BarSeries barSeries) {
		return new RuleIndicator(new MovingAverageBreakoutRule(barSeries, config), barSeries);
	}

}
