package com.stox.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.rule.VolatilityContractionBreakoutRule;
import com.stox.screener.VolatilityContractionBreakoutScreener.VolatilityContractionBreakoutConfig;

import lombok.Data;

public class VolatilityContractionBreakoutScreener implements Screener<VolatilityContractionBreakoutConfig> {

	@Data
	public static class VolatilityContractionBreakoutConfig implements ScreenerConfig {
		private int barCount = 34;
		private double minValue = 0.9;
	}

	@Override
	public String toString() {
		return "Volatility Contration Breakout";
	}
	
	@Override
	public VolatilityContractionBreakoutConfig createConfig() {
		return new VolatilityContractionBreakoutConfig();
	}

	@Override
	public ConfigView createConfigView(VolatilityContractionBreakoutConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Rule createRule(VolatilityContractionBreakoutConfig config, BarSeries barSeries) {
		return new VolatilityContractionBreakoutRule(barSeries, config.getBarCount(), config.getMinValue());
	}
	
}
