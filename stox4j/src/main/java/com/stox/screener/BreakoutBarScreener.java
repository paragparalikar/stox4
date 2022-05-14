package com.stox.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.rule.SimpleBreakoutBarRule;
import com.stox.screener.ScreenerConfig.FixedBarCountScreenerConfig;

public class BreakoutBarScreener implements Screener<FixedBarCountScreenerConfig> {

	@Override
	public String toString() {
		return "Breakout Bar";
	}
	
	@Override
	public FixedBarCountScreenerConfig createConfig() {
		return new FixedBarCountScreenerConfig(2);
	}

	@Override
	public ConfigView createConfigView(FixedBarCountScreenerConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Rule createRule(FixedBarCountScreenerConfig config, BarSeries barSeries) {
		return new SimpleBreakoutBarRule(barSeries);
	}

}
