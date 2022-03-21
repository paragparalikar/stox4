package com.stox.ml;

import com.stox.StoxApplicationContext;
import com.stox.ml.screener.BuyTradeSuccessScreener;
import com.stox.ml.screener.BuyTradeSuccessScreener.BuyTradeSuccessConfig;
import com.stox.ml.screener.LiquidityScreener;
import com.stox.rule.LiquidityRule.LiquidityConfig;

import lombok.Getter;

@Getter
public class StoxMachineLearningContext {

	private final StoxApplicationContext appContext = new StoxApplicationContext();
	
	private final LiquidityConfig liquidityConfig = new LiquidityConfig();
	private final LiquidityScreener liquidityScreener = new LiquidityScreener();
	private final BarSeriesNormalizer barSeriesNormalizer = new BarSeriesNormalizer();
	private final ScreenerDataGenerator screenerDataGenerator = new ScreenerDataGenerator(appContext, this);
	private final BuyTradeSuccessConfig buyTradeSuccessConfig = new BuyTradeSuccessConfig();
	private final BuyTradeSuccessScreener buyTradeSuccessScreener = new BuyTradeSuccessScreener();

}
