package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.TRIndicator;
import org.ta4j.core.num.Num;

import com.stox.common.ui.ConfigView;

public class PlottableTRIndicator implements PlottableLineIndicator<Void>{

	@Override
	public String toString() {
		return "True Range (TR)";
	}

	@Override
	public Void createConfig() {
		return null;
	}

	@Override
	public ConfigView createConfigView(Void config) {
		return null;
	}

	@Override
	public Indicator<Num> createIndicator(Void config, BarSeries barSeries) {
		return new TRIndicator(barSeries);
	}

}
