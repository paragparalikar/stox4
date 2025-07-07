package com.stox.charting.plot.rule;

import com.stox.charting.plot.Plottable;
import com.stox.charting.unit.BooleanUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.GroupUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.RuleIndicator;
import com.stox.rule.PullbackRule;
import javafx.scene.Group;
import javafx.scene.Node;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

public class PlottablePullbackRule implements Plottable<Boolean, PullbackRule.PullbackRuleConfig, Node> {

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
    public PullbackRule.PullbackRuleConfig createConfig() {
        return new PullbackRule.PullbackRuleConfig();
    }

    @Override
    public ConfigView createConfigView(PullbackRule.PullbackRuleConfig config) {
        return new AutoForm(config);
    }

    @Override
    public Indicator<Boolean> createIndicator(PullbackRule.PullbackRuleConfig config, BarSeries barSeries) {
        return new RuleIndicator(new PullbackRule(barSeries, config), barSeries);
    }

    @Override
    public String toString() {
        return "Pullback";
    }
}
