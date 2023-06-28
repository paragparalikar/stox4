package com.stox.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.rule.LiquidityRule;
import com.stox.rule.LiquidityRule.LiquidityConfig;

public class LiquidityScreener implements Screener<LiquidityConfig> {
	
	@Override
	public String toString() {
		return "Liquidity";
	}

	@Override
	public LiquidityConfig createConfig() {
		return new LiquidityConfig();
	}

	@Override
	public ConfigView createConfigView(LiquidityConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Rule createRule(LiquidityConfig config, BarSeries barSeries) {
		return new LiquidityRule(barSeries, config.getBarCount(), config.getMinAmount());
	}

}
