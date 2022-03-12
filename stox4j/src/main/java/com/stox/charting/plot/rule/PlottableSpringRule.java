package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.charting.plot.Plottable;
import com.stox.charting.unit.BooleanUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.GroupUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.RuleIndicator;
import com.stox.rule.SpringRule;
import com.stox.rule.SpringRule.SpringRuleConfig;

import javafx.scene.Group;
import javafx.scene.Node;

public class PlottableSpringRule implements Plottable<Boolean, SpringRuleConfig, Node> {
	
	@Override
	public String toString() {
		return "Spring";
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
	public SpringRuleConfig createConfig() {
		return new SpringRuleConfig();
	}

	@Override
	public ConfigView createConfigView(SpringRuleConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Boolean> createIndicator(SpringRuleConfig config, BarSeries barSeries) {
		return new RuleIndicator(new SpringRule(barSeries, config), barSeries);
	}

}
