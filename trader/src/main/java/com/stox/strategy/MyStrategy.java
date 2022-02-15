package com.stox.strategy;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.rules.ChainRule;
import org.ta4j.core.rules.helper.ChainLink;

import com.stox.rule.ATRStopLossRule;
import com.stox.rule.BullishBarRule;
import com.stox.rule.PriceRejectionRule;
import com.stox.rule.TrailingATRStopLossRule;
import com.stox.rule.VolatilityContractionRule;

import lombok.Builder;
import lombok.experimental.Delegate;

public class MyStrategy implements Strategy {
	
	private final int priceRejectionBarCount;
	private final int priceRejectionThreshold;
	private final double priceRejectionAtrMultiple;
	private final int volatilityContractionBarCount; 
	private final int volatilityContractionThreshold;
	private final double volatilityContractionMinStrength;
	@Delegate private transient volatile Strategy delegate;
	
	@Builder
	public MyStrategy(BarSeries barSeries, 
			int breakoutBarCount,
			int priceRejectionBarCount, 
			int priceRejectionThreshold,
			double priceRejectionAtrMultiple, 
			int volatilityContractionBarCount, 
			int volatilityContractionThreshold,
			double volatilityContractionMinStrength,
			double stopLossATRMultiple,
			int stopLossATRBarCount,
			double trailingStopLossATRMultiple,
			int trailingStopLossATRBarCount) {
		this.priceRejectionBarCount = priceRejectionBarCount;
		this.priceRejectionThreshold = priceRejectionThreshold;
		this.priceRejectionAtrMultiple = priceRejectionAtrMultiple;
		this.volatilityContractionBarCount = volatilityContractionBarCount;
		this.volatilityContractionThreshold = volatilityContractionThreshold;
		this.volatilityContractionMinStrength = volatilityContractionMinStrength;
		
		final PriceRejectionRule priceRejectionRule = new PriceRejectionRule(barSeries, priceRejectionBarCount, priceRejectionAtrMultiple);
		final ChainLink priceRejectionChainLink = new ChainLink(priceRejectionRule, priceRejectionThreshold);
		final VolatilityContractionRule volatilityContractionRule = new VolatilityContractionRule(barSeries, volatilityContractionBarCount, volatilityContractionMinStrength);
		final ChainLink volatilityContractionChainLink = new ChainLink(volatilityContractionRule, volatilityContractionThreshold);
		final BullishBarRule bullishBarRule = new BullishBarRule(barSeries, breakoutBarCount);
		final ChainRule entryRule = new ChainRule(bullishBarRule, volatilityContractionChainLink, priceRejectionChainLink);
		
		final Rule exitRule = new ATRStopLossRule(barSeries, stopLossATRBarCount, stopLossATRMultiple)
				.or(new TrailingATRStopLossRule(barSeries, trailingStopLossATRBarCount, trailingStopLossATRMultiple));
		
		this.delegate = new BaseStrategy(entryRule, exitRule);
	}

}
