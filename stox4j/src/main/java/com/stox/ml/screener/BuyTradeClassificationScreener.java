package com.stox.ml.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.ml.rule.BuyTradeClassificationRule;
import com.stox.ml.screener.BuyTradeClassificationScreener.BuyTradeClassificationConfig;
import com.stox.screener.Screener;
import com.stox.screener.ScreenerConfig;

import lombok.Data;

public class BuyTradeClassificationScreener implements Screener<BuyTradeClassificationConfig> {

	@Data
	public static class BuyTradeClassificationConfig implements ScreenerConfig {
		private int barCount = 34;
		private int entryBarCount = 2;
		private double minLiquidityAmount = 10_00_000;
		private double minGainPercentage = 20;
		private double maxLossPercentage = 10;
	}

	@Override
	public BuyTradeClassificationConfig createConfig() {
		return new BuyTradeClassificationConfig();
	}

	@Override
	public ConfigView createConfigView(BuyTradeClassificationConfig config) {
		return null;
	}

	@Override
	public Rule createRule(BuyTradeClassificationConfig config, BarSeries barSeries) {
		return new BuyTradeClassificationRule(barSeries, config.getBarCount(), config.getEntryBarCount(),
				config.getMinLiquidityAmount(), config.getMinGainPercentage(), config.getMaxLossPercentage());
	}
	
}
