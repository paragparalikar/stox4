package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.candles.DojiIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.BooleanIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;

import com.stox.charting.plot.rule.PlottableDojiRule.DojiConfig;
import com.stox.indicator.RuleIndicator;

import lombok.Data;

public class PlottableDojiRule extends AbstractPlottableRule<DojiConfig> {

	@Data
	public static class DojiConfig {
		private int barCount = 8;
		private double bodyFactor = 0.2;
		private int stochasticsBarCount = 21;
		private double stochasticsMaxK = 30;
		public String toString() {return String.format("BarCount: %d, BodyFactor: %f", barCount, bodyFactor);}
	}
	
	@Override
	public String toString() {
		return "Candlestick Pattern - Doji";
	}

	@Override
	public DojiConfig createConfig() {
		return new DojiConfig();
	}

	@Override
	public Indicator<Boolean> createIndicator(DojiConfig config, BarSeries barSeries) {
		final Indicator<Num> stochasticsKIndicator = new StochasticOscillatorKIndicator(barSeries, config.getStochasticsBarCount());
		final Rule oversoldRule = new UnderIndicatorRule(stochasticsKIndicator, config.getStochasticsMaxK());
		final Indicator<Boolean> dojiIndicator = new DojiIndicator(barSeries, config.getBarCount(), config.getBodyFactor());
		final Rule dojiRule = new BooleanIndicatorRule(dojiIndicator);
		final Rule rule = dojiRule.and(oversoldRule);
		return new RuleIndicator(rule, barSeries);
	}
	
}
