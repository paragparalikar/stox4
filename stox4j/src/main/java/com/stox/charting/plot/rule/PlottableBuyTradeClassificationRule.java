package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;

import com.stox.charting.plot.rule.PlottableBuyTradeClassificationRule.BuyTradeClassificationConfig;
import com.stox.indicator.RuleIndicator;
import com.stox.ml.rule.BuyTradeClassificationRule;

import lombok.Data;

public class PlottableBuyTradeClassificationRule extends AbstractPlottableRule<BuyTradeClassificationConfig> {

	@Data
	public static class BuyTradeClassificationConfig {
		private int barCount = 34;
		private int entryBarCount = 2;
		private double minLiquidityAmount = 10_00_000;
		private double minGainPercentage = 10; 
		private double maxLossPercentage = 5;
	}

	@Override
	public BuyTradeClassificationConfig createConfig() {
		return new BuyTradeClassificationConfig();
	}

	@Override
	public Indicator<Boolean> createIndicator(BuyTradeClassificationConfig config, BarSeries barSeries) {
		final Rule rule = new BuyTradeClassificationRule(barSeries, config.getBarCount(), config.getEntryBarCount(), 
				config.getMinLiquidityAmount(), config.getMinGainPercentage(),config.getMaxLossPercentage());
		return new RuleIndicator(rule, barSeries);
	}
	
}
