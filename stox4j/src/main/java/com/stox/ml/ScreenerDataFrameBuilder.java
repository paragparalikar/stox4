package com.stox.ml;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;

import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.ml.domain.Row;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider.BuyTradeClassConfig;
import com.stox.ml.screener.BuyTradeSuccessScreener;
import com.stox.ml.screener.BuyTradeSuccessScreener.BuyTradeSuccessConfig;
import com.stox.ml.screener.LiquidityScreener;
import com.stox.ml.screener.LiquidityScreener.LiquidityConfig;
import com.stox.screener.Screener;
import com.stox.screener.ScreenerConfig;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@RequiredArgsConstructor
public class ScreenerDataFrameBuilder {

	private final BarService barService;
	private final ScripService scripService;
	private final LiquidityConfig liquidityConfig;
	private final LiquidityScreener liquidityScreener;
	private final BuyTradeClassConfig classIndicatorConfig;
	private final BarSeriesNormalizer barSeriesNormalizer;
	private final RuleDataFrameBuilder ruleDataFrameBuilder;
	private final BuyTradeSuccessConfig buyTradeSuccessConfig;
	private final BuyTradeSuccessScreener buyTradeSuccessScreener;
	private final BuyTradeClassIndicatorProvider classIndicatorProvider;
	
	@SneakyThrows
	public <T extends ScreenerConfig> List<Row> build(T config, Screener<T> screener) {
		log.info("Building dataframe for screener : {}, barCount : {}", screener, config.getBarCount());
		final List<Row> rows = new LinkedList<>();
		final ExecutorService executorService = Executors.newWorkStealingPool();
		
		int count = 0;
		for(Scrip scrip : scripService.findAll()) {
			executorService.execute(() -> {
				final List<Row> scripRows = build(scrip, config, screener);
				rows.addAll(scripRows);
				log.info("Built dataframe for scrip : {}, tuples : {}", scrip.getName(), scripRows.size());
			});
			if(100 <= count++) break;
		}
		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.MINUTES);
		return rows;
	}
	
	private <T extends ScreenerConfig> List<Row> build(Scrip scrip, T config, Screener<T> screener){
		final int barCount = Math.max(config.getBarCount(), liquidityConfig.getBarCount());
		final List<Bar> bars = barService.find(scrip.getIsin(), Integer.MAX_VALUE);
		if(bars.size() >= barCount) {
			final BarSeries barSeries = new BaseBarSeries(bars);
			final Rule rule = screener.createRule(config, barSeries);
			final Rule liquidityRule = liquidityScreener.createRule(liquidityConfig, barSeries);
			final Rule classificationRule = buyTradeSuccessScreener.createRule(buyTradeSuccessConfig, barSeries);
			final Indicator<Integer> classIndicator = classIndicatorProvider.createIndicator(classIndicatorConfig, barSeries);
			final BarSeries normalizedBarSeries = barSeriesNormalizer.normalize(barSeries);
			return ruleDataFrameBuilder.build(barCount, rule, liquidityRule, classificationRule, classIndicator, normalizedBarSeries);
		}
		return Collections.emptyList();
	}
	
}
