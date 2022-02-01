package com.stox.strategy;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.ConvergenceDivergenceIndicator;
import org.ta4j.core.indicators.helpers.ConvergenceDivergenceIndicator.ConvergenceDivergenceType;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.BooleanIndicatorRule;
import org.ta4j.core.rules.StopGainRule;
import org.ta4j.core.rules.StopLossRule;
import org.ta4j.core.rules.UnderIndicatorRule;

import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.Delegate;

public class RsiRegularBullishDivergencyStrategy implements Strategy {

	@Delegate private final Strategy delegate;
	
	@Builder
	public RsiRegularBullishDivergencyStrategy(@NonNull final BarSeries barSeries, int barCount,
			double maxRSIValue, double minStrength, double minSlop, 
			double stopLossPercentage, double stopGainPercentage, int unstablePeriod) {
		final Indicator<Num> low = new LowPriceIndicator(barSeries);
		final Indicator<Num> rsi = new RSIIndicator(low, barCount);
		final Indicator<Boolean> divergence = new ConvergenceDivergenceIndicator(rsi, low, barCount,
				ConvergenceDivergenceType.positiveDivergent, minStrength, minSlop);
		
		final Rule maxRSIRule = new UnderIndicatorRule(rsi, DoubleNum.valueOf(maxRSIValue));
		final Rule divergenceRule = new BooleanIndicatorRule(divergence);
		final Rule entryRule = maxRSIRule.and(divergenceRule);
		
		final ClosePriceIndicator close = new ClosePriceIndicator(barSeries);
		final Rule stopLossRule = new StopLossRule(close, stopLossPercentage);
		final Rule stopGainRule = new StopGainRule(close, stopGainPercentage);
		final Rule exitRule = stopLossRule.or(stopGainRule);
		
		this.delegate = new BaseStrategy("RSI regular bullish divergence", entryRule, exitRule, unstablePeriod);
	}

}
