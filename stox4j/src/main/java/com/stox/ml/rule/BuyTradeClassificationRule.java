package com.stox.ml.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.rules.AbstractRule;

import com.stox.rule.LiquidityRule;

import lombok.Builder;
import lombok.experimental.Delegate;

public class BuyTradeClassificationRule extends AbstractRule {

	@Delegate private final Rule delegate;
	
	@Builder
	public BuyTradeClassificationRule(BarSeries barSeries, int barCount, int entryBarCount, 
			double minLiquidityAmount, double minGainPercentage, double maxLossPercentage) {
		final BuyOpportunityRule buyOpportunityRule = new BuyOpportunityRule(barSeries, entryBarCount);
		final LiquidityRule liquidityRule = new LiquidityRule(barSeries, barCount, minLiquidityAmount);
		final BuyTradeSuccessRule buyTradeSuccessRule = new BuyTradeSuccessRule(barSeries, barCount, minGainPercentage, maxLossPercentage);
		this.delegate = buyOpportunityRule.and(liquidityRule).and(buyTradeSuccessRule);
	}

}
