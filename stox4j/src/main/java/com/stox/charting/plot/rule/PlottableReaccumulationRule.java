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
import com.stox.rule.ReaccumulationRule;
import com.stox.rule.ReaccumulationRule.ReaccumulationRuleConfig;

import javafx.scene.Group;
import javafx.scene.Node;

public class PlottableReaccumulationRule implements Plottable<Boolean, ReaccumulationRuleConfig, Node>{

	@Override
	public String toString() {
		return "Reaccumulation";
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
	public ReaccumulationRuleConfig createConfig() {
		return new ReaccumulationRuleConfig();
	}

	@Override
	public ConfigView<ReaccumulationRuleConfig> createConfigView() {
		return null;
	}

	@Override
	public Indicator<Boolean> createIndicator(ReaccumulationRuleConfig config, BarSeries barSeries) {
		return new RuleIndicator(new ReaccumulationRule(barSeries, config), barSeries);
	}
	
}
