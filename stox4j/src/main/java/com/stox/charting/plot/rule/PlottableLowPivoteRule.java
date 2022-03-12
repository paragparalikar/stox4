package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.charting.plot.Plottable;
import com.stox.charting.unit.BooleanUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.GroupUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.indicator.RuleIndicator;
import com.stox.rule.LowPivoteRule;

import javafx.scene.Group;
import javafx.scene.Node;

public class PlottableLowPivoteRule implements Plottable<Boolean, Void, Node> {
	
	@Override
	public String toString() {
		return "Low Pivotes";
	}

	@Override
	public double resolveLowValue(Boolean model) {
		return 0;
	}

	@Override
	public double resolveHighValue(Boolean model) {
		return 0;
	}

	@Override
	public Unit<Boolean, Node> createUnit() {
		return new BooleanUnit();
	}

	@Override
	public UnitParent<Node> createUnitParent() {
		return new GroupUnitParent(new Group());
	}
	
	@Override
	public ConfigView createConfigView(Void config) {
		return null;
	}

	@Override
	public Indicator<Boolean> createIndicator(Void config, BarSeries barSeries) {
		return new RuleIndicator(new LowPivoteRule(barSeries), barSeries);
	}

	@Override
	public Void createConfig() {
		return null;
	}

}
