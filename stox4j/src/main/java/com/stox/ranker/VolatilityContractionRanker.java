package com.stox.ranker;

import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
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
		return new VolatilityContractionIndicator(barSeries, config.getBarCount());
	}
	
	public Rank rank(VolatilityContractionConfig config, Scrip scrip, BarService barService) {
		final int barCount = config.getBarCount();
		final List<Bar> bars = barService.find(scrip.getIsin(), barCount);
		if(bars.size() < barCount) {
			return new Rank(DoubleNum.valueOf(Double.MIN_VALUE), scrip);
		} else {
			final BarSeries barSeries = new BaseBarSeries(bars);
			final Indicator<Num> indicator = new VolatilityContractionIndicator(barSeries, barCount);
			final Num rankValue = indicator.getValue(barSeries.getBarCount() - 1);
			return new Rank(rankValue, scrip);
		}
	}
	
}
