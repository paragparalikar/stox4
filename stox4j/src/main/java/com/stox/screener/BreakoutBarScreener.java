package com.stox.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.rule.SimpleBreakoutBarRule;
import com.stox.rule.SimpleBreakoutBarRule.SimpleBreakoutBarRuleConfig;

public class BreakoutBarScreener implements Screener<SimpleBreakoutBarRuleConfig> {

	@Override
	public String toString() {
		return "Breakout Bar";
	}
	
	@Override
	public SimpleBreakoutBarRuleConfig createConfig() {
		return new SimpleBreakoutBarRuleConfig();
	}

	@Override
	public ConfigView createConfigView(SimpleBreakoutBarRuleConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Rule createRule(SimpleBreakoutBarRuleConfig config, BarSeries barSeries) {
		return new SimpleBreakoutBarRule(barSeries, config);
	}

}
