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
import com.stox.rule.BreakoutBarRule;
import com.stox.rule.BreakoutBarRule.BreakoutBarRuleConfig;

import javafx.scene.Group;
import javafx.scene.Node;

public class PlottableBreakoutBarRule implements Plottable<Boolean, BreakoutBarRuleConfig, Node> {
	
	@Override
	public String toString() {
		return "Breakout Bar";
	}

	@Override
	public ConfigView<BreakoutBarRuleConfig> createConfigView() {
		return null;
	}

	@Override
	public Indicator<Boolean> createIndicator(BreakoutBarRuleConfig config, BarSeries barSeries) {
		return new RuleIndicator(new BreakoutBarRule(barSeries), barSeries);
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
	public BreakoutBarRuleConfig createConfig() {
		return new BreakoutBarRuleConfig();
	}

}
