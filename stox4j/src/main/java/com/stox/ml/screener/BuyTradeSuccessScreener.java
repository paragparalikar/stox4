package com.stox.ml.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.ml.rule.BuyTradeSuccessRule;
import com.stox.ml.screener.BuyTradeSuccessScreener.BuyTradeSuccessConfig;
import com.stox.screener.Screener;
import com.stox.screener.ScreenerConfig;

import lombok.Data;

public class BuyTradeSuccessScreener implements Screener<BuyTradeSuccessConfig> {

	@Data
	public static class BuyTradeSuccessConfig implements ScreenerConfig {
		private int barCount = 13;
		private double minGainPercentage = 13;
		private double maxLossPercentage = 5;
	}

	@Override
	public BuyTradeSuccessConfig createConfig() {
		return new BuyTradeSuccessConfig();
	}

	@Override
	public ConfigView createConfigView(BuyTradeSuccessConfig config) {
		return null;
	}

	@Override
	public Rule createRule(BuyTradeSuccessConfig config, BarSeries barSeries) {
		return new BuyTradeSuccessRule(barSeries, 
				config.getBarCount(), 
				config.getMinGainPercentage(), 
				config.getMaxLossPercentage());
	}
	
	
}
