package com.stox.ml;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import com.stox.StoxApplicationContext;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider.BuyTradeClassConfig;
import com.stox.ml.screener.BuyTradeClassificationScreener.BuyTradeClassificationConfig;
import com.stox.screener.VolatilityContractionBreakoutScreener;
import com.stox.screener.VolatilityContractionBreakoutScreener.VolatilityContractionBreakoutConfig;

public class ScreenerDataGenerator {

	public static void main(String[] args) throws IOException {
		final StoxApplicationContext appContext = new StoxApplicationContext();
		final StoxMachineLearningContext mlContext = new StoxMachineLearningContext();
		final VolatilityContractionBreakoutScreener screener = new VolatilityContractionBreakoutScreener();
		final String runId = UUID.randomUUID().toString();
		final Path path = appContext.getHome().resolve(runId);
		
		// Variable configs - permutations and combinations of these
		final BuyTradeClassConfig buyTradeClassConfig = mlContext.getClassIndicatorConfig();
		final BuyTradeClassificationConfig buyTradeClassificationConfig = mlContext.getClassificationConfig();
		final VolatilityContractionBreakoutConfig ruleConfig = new VolatilityContractionBreakoutConfig();
		
		ScreenerDataFrameBuilder.builder()
				.barService(appContext.getBarService())
				.scripService(appContext.getScripService())
				.barSeriesNormalizer(mlContext.getBarSeriesNormalizer())
				.ruleDataFrameBuilder(mlContext.getRuleDataFrameBuilder())
				.classIndicatorConfig(buyTradeClassConfig)
				.classIndicatorProvider(mlContext.getClassIndicatorProvider())
				.classificationConfig(buyTradeClassificationConfig)
				.classificationScreener(mlContext.getClassificationScreener())
				.build()
				.build(path, ruleConfig, screener);
	}
	
}
