package com.stox.ml;

import com.stox.ml.indicator.BuyTradeClassIndicatorProvider;
import com.stox.ml.indicator.BuyTradeClassIndicatorProvider.BuyTradeClassConfig;
import com.stox.ml.screener.BuyTradeClassificationScreener;
import com.stox.ml.screener.BuyTradeClassificationScreener.BuyTradeClassificationConfig;

import lombok.Getter;

@Getter
public class StoxMachineLearningContext {

	private final BarSeriesNormalizer barSeriesNormalizer = new BarSeriesNormalizer();
	private final BuyTradeClassConfig classIndicatorConfig = new BuyTradeClassConfig();
	private final RuleDataFrameBuilder ruleDataFrameBuilder = new RuleDataFrameBuilder();
	private final BuyTradeClassificationConfig classificationConfig = new BuyTradeClassificationConfig();
	private final BuyTradeClassificationScreener classificationScreener = new BuyTradeClassificationScreener();
	private final BuyTradeClassIndicatorProvider classIndicatorProvider = new BuyTradeClassIndicatorProvider();
}
