package com.stox.rule;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import com.stox.ml.feature.BarSeriesFeatureExtractor;
import com.stox.ml.feature.VolatilityContractionFeatureExtractor;
import com.stox.rule.LiquidityRule.LiquidityConfig;
import com.stox.rule.VolatilityContractionBreakoutRule.VolatilityContractionBreakoutRuleConfig;

public class VolatilityContractionBreakoutML34Rule extends AbstractRule {
	private static final int BAR_COUNT = 34;
	private static final Path PATH = Paths.get("models", "Volatility Contration Breakout - 34.model");
	
	private final Rule delegate;

	public VolatilityContractionBreakoutML34Rule(BarSeries barSeries, Path home) {
		final String modelPath = home.resolve(PATH).toString();
		final BarSeriesFeatureExtractor featureExtractor = new VolatilityContractionFeatureExtractor();
		final WekaClassifierRule wekaClassifierRule = new WekaClassifierRule(barSeries, BAR_COUNT, modelPath, featureExtractor);
		
		final LiquidityConfig liquidityConfig = new LiquidityConfig();
		final LiquidityRule liquidityRule = new LiquidityRule(barSeries, liquidityConfig);
		
		final VolatilityContractionBreakoutRuleConfig config = new VolatilityContractionBreakoutRuleConfig();
		final Rule rule = new VolatilityContractionBreakoutRule(barSeries, BAR_COUNT, config);
		
		this.delegate = liquidityRule.and(rule).and(wekaClassifierRule);
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return delegate.isSatisfied(index, tradingRecord);
	}

}
