package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.IsFallingRule;

import lombok.experimental.Delegate;

public class VolatilityContractionRule extends AbstractRule {

	@Delegate private final Rule delegate;
	
	public VolatilityContractionRule(BarSeries barSeries, int barCount, double minStrength) {
		final ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
		final StandardDeviationIndicator standardDeviationIndicator = new StandardDeviationIndicator(closePriceIndicator, barCount);
		this.delegate = new IsFallingRule(standardDeviationIndicator, barCount, minStrength);
	}

}
