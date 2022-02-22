package com.stox.charting.plot;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.OpenPriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.bar.BarValueType;
import com.stox.common.ui.ConfigView;

public interface Plottable<T, C, N> {
	
	double resolveLowValue(T model);
	double resolveHighValue(T model);
	
	Unit<T, N> createUnit();
	UnitParent<N> createUnitParent();
	
	C createConfig();
	ConfigView createConfigView(C config); 
	
	Indicator<T> createIndicator(C config, BarSeries barSeries);
	
	default Indicator<Num> createIndicator(BarValueType barValueType, BarSeries barSeries){
		switch(barValueType) {
		case CLOSE: return new ClosePriceIndicator(barSeries);
		case HIGH: return new HighPriceIndicator(barSeries);
		case LOW: return new LowPriceIndicator(barSeries);
		case OPEN: return new OpenPriceIndicator(barSeries);
		case VOLUME: return new VolumeIndicator(barSeries);
		default: throw new IllegalArgumentException();
		}
	}

}
