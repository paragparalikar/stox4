package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.unit.BarUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.GroupUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.indicator.PriceRejectionIndicator;

import javafx.scene.Group;
import javafx.scene.Node;

public class PlottablePriceRejectionIndicator implements Plottable<Num, Void, Node> {
	
	@Override
	public String toString() {
		return "Price Rejection (PR)";
	}

	@Override
	public double resolveLowValue(Num model) {
		return model.doubleValue();
	}

	@Override
	public double resolveHighValue(Num model) {
		return model.doubleValue();
	}

	@Override
	public Unit<Num, Node> createUnit() {
		return new BarUnit();
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
	public ConfigView createConfigView(Void config) {
		return null;
	}

	@Override
	public Indicator<Num> createIndicator(Void config, BarSeries barSeries) {
		return new PriceRejectionIndicator(barSeries);
	}

	
	
}
