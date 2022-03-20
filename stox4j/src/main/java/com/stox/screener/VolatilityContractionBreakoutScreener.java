package com.stox.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.rule.VolatilityContractionBreakoutRule;
import com.stox.rule.VolatilityContractionBreakoutRule.VolatilityContractionBreakoutRuleConfig;

public class VolatilityContractionBreakoutScreener implements Screener<VolatilityContractionBreakoutRuleConfig> {

	@Override
	public String toString() {
		return "Volatility Contration Breakout";
	}
	
	@Override
	public VolatilityContractionBreakoutRuleConfig createConfig() {
		return new VolatilityContractionBreakoutRuleConfig();
	}

	@Override
	public ConfigView createConfigView(VolatilityContractionBreakoutRuleConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Rule createRule(VolatilityContractionBreakoutRuleConfig config, BarSeries barSeries) {
		return new VolatilityContractionBreakoutRule(barSeries, config.getBarCount(), config);
	}
	
}
