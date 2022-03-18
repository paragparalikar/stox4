package com.stox.ml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;

import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider.BuyTradeClassConfig;
import com.stox.ml.screener.BuyTradeClassificationScreener;
import com.stox.ml.screener.BuyTradeClassificationScreener.BuyTradeClassificationConfig;
import com.stox.screener.Screener;
import com.stox.screener.ScreenerConfig;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.io.Write;

@Slf4j
@Builder
@RequiredArgsConstructor
public class ScreenerDataFrameBuilder {

	private final BarService barService;
	private final ScripService scripService;
	private final BuyTradeClassConfig classIndicatorConfig;
	private final BarSeriesNormalizer barSeriesNormalizer;
	private final RuleDataFrameBuilder ruleDataFrameBuilder;
	private final BuyTradeClassificationConfig classificationConfig;
	private final BuyTradeClassificationScreener classificationScreener;
	private final BuyTradeClassIndicatorProvider classIndicatorProvider;
	
	@SneakyThrows
	public <T extends ScreenerConfig> void build(Path path, T config, Screener<T> screener) {
		Files.createDirectories(path);
		log.info("Building dataframe for screener : {}, barCount : {}", screener, config.getBarCount());
		for(Scrip scrip : scripService.findAll()) {
			final List<Tuple> tuples = build(scrip, config, screener);
			if(null != tuples && !tuples.isEmpty()) {
				final DataFrame dataFrame = DataFrame.of(tuples);
				Write.csv(dataFrame, path.resolve(scrip.getCode() + ".csv"));
				log.info("Built dataframe for scrip : {}, tuples : {}", scrip.getName(), tuples.size());
			}
		}
	}
	
	private <T extends ScreenerConfig> List<Tuple> build(Scrip scrip, T config, Screener<T> screener){
		final int barCount = Math.max(config.getBarCount(), classificationConfig.getBarCount());
		final List<Bar> bars = barService.find(scrip.getIsin(), Integer.MAX_VALUE);
		if(bars.size() >= barCount) {
			final BarSeries barSeries = new BaseBarSeries(bars);
			final BarSeries normalizedBarSeries = barSeriesNormalizer.normalize(barSeries);
			final Rule rule = screener.createRule(config, normalizedBarSeries);
			final Indicator<Integer> classIndicator = classIndicatorProvider.createIndicator(classIndicatorConfig, barSeries);
			final Rule classificationRule = classificationScreener.createRule(classificationConfig, barSeries);
			return ruleDataFrameBuilder.build(barCount, rule, classificationRule, classIndicator, normalizedBarSeries);
		}
		return Collections.emptyList();
	}
	
}
