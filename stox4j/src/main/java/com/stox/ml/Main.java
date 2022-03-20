package com.stox.ml;

import java.io.IOException;
import java.nio.file.Path;

import com.stox.ml.feature.VolatilityContractionFeatureExtractor;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider.BuyTradeClassConfig;
import com.stox.ml.screener.BuyTradeSuccessScreener.BuyTradeSuccessConfig;
import com.stox.ml.screener.LiquidityScreener.LiquidityConfig;
import com.stox.rule.VolatilityContractionBreakoutRule.VolatilityContractionBreakoutRuleConfig;
import com.stox.screener.VolatilityContractionBreakoutScreener;

public class Main {

	public static void main(String[] args) throws IOException {
		generateData();
	}
	
	private static void generateData() {
		final StoxMachineLearningContext mlContext = new StoxMachineLearningContext();
		final VolatilityContractionBreakoutScreener screener = new VolatilityContractionBreakoutScreener();
		final VolatilityContractionFeatureExtractor featureExtractor = new VolatilityContractionFeatureExtractor();
		
		// Variable configs - permutations and combinations of these
		final LiquidityConfig liquidityConfig = mlContext.getLiquidityConfig();
		final BuyTradeClassConfig buyTradeClassConfig = mlContext.getClassIndicatorConfig();
		final BuyTradeSuccessConfig buyTradeSuccessConfig = mlContext.getBuyTradeSuccessConfig();
		final VolatilityContractionBreakoutRuleConfig ruleConfig = new VolatilityContractionBreakoutRuleConfig();
		
		final Path path = mlContext.getAppContext().getHome().resolve(screener.toString() + " - " + ruleConfig.getBarCount() + "-test.csv");
		mlContext.getScreenerDataGenerator().generate(liquidityConfig, buyTradeClassConfig, 
				buyTradeSuccessConfig, featureExtractor, ruleConfig, screener, path);
	}
	
}
