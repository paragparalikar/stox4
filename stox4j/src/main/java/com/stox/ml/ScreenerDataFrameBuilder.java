package com.stox.ml;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Rule;

import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.screener.Screener;
import com.stox.screener.ScreenerConfig;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import smile.data.DataFrame;
import smile.data.Tuple;

@Slf4j
@Builder
@RequiredArgsConstructor
public class ScreenerDataFrameBuilder {

	private final BarService barService;
	private final ScripService scripService;
	private final BarSeriesNormalizer barSeriesNormalizer;
	private final RuleDataFrameBuilder ruleDataFrameBuilder;
	
	public <T extends ScreenerConfig> DataFrame build(T config, Screener<T> screener) {
		final List<Tuple> tuples = new LinkedList<>();
		log.info("Building dataframe for screener : {}, barCount : {}", screener, config.getBarCount());
		for(Scrip scrip : scripService.findAll()) {
			final List<Tuple> ruleTuples = build(scrip, config, screener);
			log.info("Built dataframe for scrip : {}, tuples : {}", scrip.getName(), ruleTuples.size());
			tuples.addAll(ruleTuples);
		}
		return DataFrame.of(tuples);
	}
	
	private <T extends ScreenerConfig> List<Tuple> build(Scrip scrip, T config, Screener<T> screener){
		final List<Bar> bars = barService.find(scrip.getIsin(), Integer.MAX_VALUE);
		if(bars.size() >= config.getBarCount()) {
			final BarSeries barSeries = new BaseBarSeries(bars);
			final BarSeries normalizedBarSeries = barSeriesNormalizer.normalize(barSeries);
			final Rule rule = screener.createRule(config, normalizedBarSeries);
			return ruleDataFrameBuilder.build(rule, normalizedBarSeries);
		}
		return Collections.emptyList();
	}
	
}
