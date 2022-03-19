package com.stox.ml;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.stox.StoxApplicationContext;
import com.stox.ml.domain.Row;
import com.stox.ml.feature.BarSeriesFeatureExtractor;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider.BuyTradeClassConfig;
import com.stox.ml.screener.BuyTradeSuccessScreener.BuyTradeSuccessConfig;
import com.stox.ml.screener.LiquidityScreener.LiquidityConfig;
import com.stox.screener.Screener;
import com.stox.screener.ScreenerConfig;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ScreenerDataGenerator {

	private final StoxApplicationContext appContext;
	private final StoxMachineLearningContext mlContext;
	
	@SneakyThrows
	public <T extends ScreenerConfig> void generate(
			LiquidityConfig liquidityConfig, 
			BuyTradeClassConfig buyTradeClassConfig, 
			BuyTradeSuccessConfig buyTradeSuccessConfig, 
			BarSeriesFeatureExtractor barSeriesFeatureExtractor,
			T ruleConfig, Screener<T> screener, Path path) {
		
		final List<Row> rows = ScreenerDataFrameBuilder.builder()
				.barService(appContext.getBarService())
				.scripService(appContext.getScripService())
				.liquidityConfig(liquidityConfig)
				.liquidityScreener(mlContext.getLiquidityScreener())
				.barSeriesNormalizer(mlContext.getBarSeriesNormalizer())
				.ruleDataFrameBuilder(new RuleDataFrameBuilder(barSeriesFeatureExtractor))
				.classIndicatorConfig(buyTradeClassConfig)
				.classIndicatorProvider(mlContext.getClassIndicatorProvider())
				.buyTradeSuccessConfig(buyTradeSuccessConfig)
				.buyTradeSuccessScreener(mlContext.getBuyTradeSuccessScreener())
				.build()
				.build(ruleConfig, screener);
		
		if(null != rows && !rows.isEmpty()) {
			if(1 < rows.stream()
					.map(row -> row.getFeatures().size())
					.distinct()
					.count()) {
				throw new IllegalArgumentException("Uneven features size");
			}
			writeCsv(path, rows);
		} else {
			throw new Exception("Could not extract any rows");
		}
	}
	
	@SneakyThrows
	private void writeCsv(Path path, List<Row> rows) {
		Files.createDirectories(path.getParent());
		try (BufferedWriter writer = Files.newBufferedWriter(path)){
			writer.write(rows.get(0).getHeaderCsv());
			writer.newLine();
			for(Row row : rows) {
				writer.write(row.toCsv());
				writer.newLine();
			}
		}
	}
	
}
