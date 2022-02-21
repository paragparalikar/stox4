package com.stox.charting.plot.price;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.charting.plot.Plottable;
import com.stox.charting.unit.CandleUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.GroupUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.indicator.BarIndicator;

import javafx.scene.Group;
import javafx.scene.Node;

public class PlottableBarIndicator implements Plottable<Bar, Void, Node> {

	@Override
	public double resolveLowValue(Bar model) {
		return model.getLowPrice().doubleValue();
	}

	@Override
	public double resolveHighValue(Bar model) {
		return model.getHighPrice().doubleValue();
	}

	@Override
	public Unit<Bar, Node> createUnit() {
		return new CandleUnit();
	}

	@Override
	public UnitParent<Node> createUnitParent() {
		return new GroupUnitParent(new Group());
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
	public Indicator<Bar> createIndicator(Void config, BarSeries barSeries) {
		return new BarIndicator(barSeries);
	}

}
