package com.stox.rule;

import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ConvergenceDivergenceIndicator;
import org.ta4j.core.indicators.helpers.ConvergenceDivergenceIndicator.ConvergenceDivergenceType;
import org.ta4j.core.indicators.helpers.PriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

import lombok.Builder;

public class RSIDivergenceRule extends AbstractRule {

	private final Indicator<Boolean> delegate;
	
	@Builder
	public RSIDivergenceRule(PriceIndicator priceIndicator, int barCount, ConvergenceDivergenceType type, 
			double minStrength, double minSlope) {
		final Indicator<Num> rsiIndicator = new RSIIndicator(priceIndicator, barCount);
		this.delegate = new ConvergenceDivergenceIndicator(priceIndicator, rsiIndicator, barCount, type, minStrength, minSlope);
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return delegate.getValue(index);
	}

}
