package com.stox.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.rule.MovingAverageBreakoutRule;
import com.stox.rule.MovingAverageBreakoutRule.MovingAverageBreakoutRuleConfig;

public class MovingAverageBreakoutScreener implements Screener<MovingAverageBreakoutRuleConfig> {

	@Override
	public String toString() {
		return "Moving Average Breakout";
	}
	
	@Override
	public MovingAverageBreakoutRuleConfig createConfig() {
		return new MovingAverageBreakoutRuleConfig();
	}

	@Override
	public ConfigView createConfigView(MovingAverageBreakoutRuleConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Rule createRule(MovingAverageBreakoutRuleConfig config, BarSeries barSeries) {
		return new MovingAverageBreakoutRule(barSeries, config);
	}

}
