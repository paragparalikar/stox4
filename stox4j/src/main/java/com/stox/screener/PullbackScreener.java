package com.stox.screener;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.rule.PullbackRule;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

public class PullbackScreener implements Screener<PullbackRule.PullbackRuleConfig> {

    @Override
    public PullbackRule.PullbackRuleConfig createConfig() {
        return new PullbackRule.PullbackRuleConfig();
    }

    @Override
    public ConfigView createConfigView(PullbackRule.PullbackRuleConfig config) {
        return new AutoForm(config);
    }

    @Override
    public Rule createRule(PullbackRule.PullbackRuleConfig config, BarSeries barSeries) {
        return new PullbackRule(barSeries, config);
    }

    @Override
    public String toString() {
        return "Pullback";
    }
}
