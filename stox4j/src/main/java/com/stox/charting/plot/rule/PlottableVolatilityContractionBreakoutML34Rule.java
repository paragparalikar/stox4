package com.stox.charting.plot.rule;

import java.nio.file.Path;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.indicator.RuleIndicator;
import com.stox.rule.VolatilityContractionBreakoutML34Rule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlottableVolatilityContractionBreakoutML34Rule extends AbstractPlottableRule<Void> {

	private final Path home;
	
	@Override
	public Void createConfig() {
		return null;
	}

	@Override
	public Indicator<Boolean> createIndicator(Void config, BarSeries barSeries) {
		return new RuleIndicator(new VolatilityContractionBreakoutML34Rule(barSeries, home), barSeries);
	}

}
