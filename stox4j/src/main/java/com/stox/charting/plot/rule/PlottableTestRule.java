package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.indicator.RuleIndicator;
import com.stox.rule.TestRule;
import com.stox.rule.TestRule.TestConfig;

public class PlottableTestRule extends AbstractPlottableRule<TestConfig> {

	@Override
	public String toString() {
		return "Test";
	}
	
	@Override
	public TestConfig createConfig() {
		return new TestConfig();
	}

	@Override
	public Indicator<Boolean> createIndicator(TestConfig config, BarSeries series) {
		return new RuleIndicator(new TestRule(series, config), series);
	}

}
