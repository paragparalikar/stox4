package com.stox.rule;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;

import com.stox.ml.feature.VolatilityContractionFeatureExtractor;
import com.stox.rule.VolatilityContractionBreakoutRule.VolatilityContractionBreakoutRuleConfig;

public class VolatilityContractionBreakoutMLRule extends AbstractMachineLearningRule {
	
	private final VolatilityContractionBreakoutRule delegate;

	public VolatilityContractionBreakoutMLRule(BarSeries barSeries, int barCount, Path home) {
		super(barSeries, barCount, 
				home.resolve(Paths.get("models",
						"VolatilityContractionBreakout-" + barCount + "-SMOTE-STRICT.model")).toString(), 
				new VolatilityContractionFeatureExtractor());
		delegate = new VolatilityContractionBreakoutRule(barSeries, barCount, new VolatilityContractionBreakoutRuleConfig());
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return super.isSatisfied(index, tradingRecord);
	}

}
