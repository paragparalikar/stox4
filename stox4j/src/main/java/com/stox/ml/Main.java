package com.stox.ml;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;

import com.stox.ml.feature.VolatilityContractionFeatureExtractor;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider.BuyTradeClassConfig;
import com.stox.ml.screener.BuyTradeSuccessScreener.BuyTradeSuccessConfig;
import com.stox.ml.screener.LiquidityScreener.LiquidityConfig;
import com.stox.screener.VolatilityContractionBreakoutScreener;
import com.stox.screener.VolatilityContractionBreakoutScreener.VolatilityContractionBreakoutConfig;

import lombok.SneakyThrows;
import smile.classification.RandomForest;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.type.StructType;
import smile.io.Read;

public class Main {

	public static void main(String[] args) throws IOException {
		generateData();
		//test(Paths.get("C:\\Users\\parag\\.stox4j", "Volatility Contration Breakout - 34.csv"));
	}
	
	@SneakyThrows
	private static void test(Path path) {
		final StructType structType = BarSeriesTuple.builder().barCount(34).build().schema();
		final DataFrame dataFrame = Read.csv(path, CSVFormat.DEFAULT, structType);
		final RandomForest rf = RandomForest.fit(Formula.lhs("class"), dataFrame); 
		final int[] result = rf.predict(dataFrame); final Map<Integer, Integer>
		report = new HashMap<>(); for(int i : result) { report.put(i,
		report.getOrDefault(i, 0) + 1); } report.forEach((item, count) -> {
		System.out.println(String.format("Class : %d\tCount : %d", item, count)); });
	}
	
	private static void generateData() {
		final StoxMachineLearningContext mlContext = new StoxMachineLearningContext();
		final VolatilityContractionBreakoutScreener screener = new VolatilityContractionBreakoutScreener();
		final VolatilityContractionFeatureExtractor featureExtractor = new VolatilityContractionFeatureExtractor();
		
		// Variable configs - permutations and combinations of these
		final LiquidityConfig liquidityConfig = mlContext.getLiquidityConfig();
		final BuyTradeClassConfig buyTradeClassConfig = mlContext.getClassIndicatorConfig();
		final BuyTradeSuccessConfig buyTradeSuccessConfig = mlContext.getBuyTradeSuccessConfig();
		final VolatilityContractionBreakoutConfig ruleConfig = new VolatilityContractionBreakoutConfig();
		
		final Path path = mlContext.getAppContext().getHome().resolve(screener.toString() + " - " + ruleConfig.getBarCount() + ".csv");
		mlContext.getScreenerDataGenerator().generate(liquidityConfig, buyTradeClassConfig, 
				buyTradeSuccessConfig, featureExtractor, ruleConfig, screener, path);
	}
	
}
