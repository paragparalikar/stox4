package com.stox.charting.plot.price;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.charting.plot.Plottable;
import com.stox.charting.unit.CandleUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.indicator.BarIndicator;

public class PlottableBarIndicator implements Plottable<Bar, Void> {

	@Override
	public double resolveLowValue(Bar model) {
		return model.getLowPrice().doubleValue();
	}

	@Override
	public double resolveHighValue(Bar model) {
		return model.getHighPrice().doubleValue();
	}

	@Override
	public Unit<Bar> createUnit() {
		return new CandleUnit();
	}

	@Override
	public UnitParent<Bar> createUnitParent() {
		return null;
	}

	@Override
	public Void createConfig() {
		return null;
	}

	@Override
	public ConfigView<Void> createConfigView() {
		return null;
	}

	@Override
	public Indicator<Bar> createIndicator(BarSeries barSeries) {
		return new BarIndicator(barSeries);
	}

}
