package com.stox.charting.plot;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;

public interface Plottable<T, C> {
	
	double resolveLowValue(T model);
	double resolveHighValue(T model);
	
	Unit<T> createUnit();
	UnitParent<T> createUnitParent();
	
	C createConfig();
	ConfigView<C> createConfigView(); 
	
	Indicator<T> createIndicator(BarSeries barSeries);

}
