package com.stox.ranker;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.common.ui.ConfigView;

public interface Ranker<C extends RankerConfig> {

	C createConfig();
	ConfigView createConfigView(C config); 
	Indicator<Num> createIndicator(C config, BarSeries barSeries);
	
}
