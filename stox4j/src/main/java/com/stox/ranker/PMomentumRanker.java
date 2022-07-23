package com.stox.ranker;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.PMomentumIndicator;
import com.stox.ranker.PMomentumRanker.PMomentumRankerConfig;

import lombok.Data;

public class PMomentumRanker implements Ranker<PMomentumRankerConfig> {

	@Override
	public String toString() {
		return "PMomentum";
	}
	
	@Data
	public static class PMomentumRankerConfig implements RankerConfig {
		private int barCount = 200;
	}

	@Override
	public PMomentumRankerConfig createConfig() {
		return new PMomentumRankerConfig();
	}

	@Override
	public ConfigView createConfigView(PMomentumRankerConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(PMomentumRankerConfig config, BarSeries barSeries) {
		return new PMomentumIndicator(config.getBarCount(), barSeries);
	}
	
}
