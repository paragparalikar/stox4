package com.stox.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;

public interface Screener<C extends ScreenerConfig> {

	C createConfig();
	ConfigView createConfigView(C config); 
	Rule createRule(C config, BarSeries barSeries);
	
}
