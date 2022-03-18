package com.stox.ml;

import com.stox.ml.indicator.BuyTradeClassIndicatorProvider;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider.BuyTradeClassConfig;
import com.stox.ml.screener.BuyTradeSuccessScreener;
import com.stox.ml.screener.BuyTradeSuccessScreener.BuyTradeSuccessConfig;
import com.stox.ml.screener.LiquidityScreener;
import com.stox.ml.screener.LiquidityScreener.LiquidityConfig;

import lombok.Getter;

@Getter
public class StoxMachineLearningContext {

	private final LiquidityConfig liquidityConfig = new LiquidityConfig();
	private final LiquidityScreener liquidityScreener = new LiquidityScreener();
	private final BarSeriesNormalizer barSeriesNormalizer = new BarSeriesNormalizer();
	private final BuyTradeClassConfig classIndicatorConfig = new BuyTradeClassConfig();
	private final RuleDataFrameBuilder ruleDataFrameBuilder = new RuleDataFrameBuilder();
	private final BuyTradeSuccessConfig buyTradeSuccessConfig = new BuyTradeSuccessConfig();
	private final BuyTradeSuccessScreener buyTradeSuccessScreener = new BuyTradeSuccessScreener();
	private final BuyTradeClassIndicatorProvider classIndicatorProvider = new BuyTradeClassIndicatorProvider();
}
