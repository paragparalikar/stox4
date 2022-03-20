package com.stox.ml.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.ml.screener.LiquidityScreener.LiquidityConfig;
import com.stox.rule.LiquidityRule;
import com.stox.screener.Screener;
import com.stox.screener.ScreenerConfig;

import lombok.Data;

public class LiquidityScreener implements Screener<LiquidityConfig> {

	@Data
	public static class LiquidityConfig implements ScreenerConfig {
		private int barCount = 55;
		private double minAmount = 10_00_000;
	}

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
