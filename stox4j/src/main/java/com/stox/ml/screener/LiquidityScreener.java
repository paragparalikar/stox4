package com.stox.ml.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.rule.LiquidityRule;
import com.stox.rule.LiquidityRule.LiquidityConfig;
import com.stox.screener.Screener;

public class LiquidityScreener implements Screener<LiquidityConfig> {

	@Override
	public LiquidityConfig createConfig() {
		return new LiquidityConfig();
	}

	@Override
	public ConfigView createConfigView(LiquidityConfig config) {
		return null;
	}

	@Override
	public Rule createRule(LiquidityConfig config, BarSeries barSeries) {
		return new LiquidityRule(barSeries, config.getBarCount(), config.getMinAmount());
	}
	
}
