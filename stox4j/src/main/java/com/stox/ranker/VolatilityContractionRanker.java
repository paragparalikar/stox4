package com.stox.ranker;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.num.Num;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.VolatilityContractionIndicator;
import com.stox.ranker.VolatilityContractionRanker.VolatilityContractionConfig;

import lombok.Data;

public class VolatilityContractionRanker implements Ranker<VolatilityContractionConfig> {

	@Data
	public static class VolatilityContractionConfig implements RankerConfig {
		private int barCount = 34;
	}

	@Override
	public String toString() {
		return "Volatility Contraction";
	}
	
	@Override
	public VolatilityContractionConfig createConfig() {
		return new VolatilityContractionConfig();
	}

	@Override
	public ConfigView createConfigView(VolatilityContractionConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(VolatilityContractionConfig config, BarSeries barSeries) {
		final Indicator<Num> indicator = new VolatilityContractionIndicator(barSeries, config.getBarCount());
		return new StochasticRSIIndicator(indicator, config.getBarCount());
	}
	
}
