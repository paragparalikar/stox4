package com.stox.ranker;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import lombok.Data;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.num.Num;

public class AccumulationRanker implements Ranker<AccumulationRanker.AccumulationRankerConfig> {

    @Override
    public AccumulationRankerConfig createConfig() {
        return new AccumulationRankerConfig();
    }

    @Override
    public ConfigView createConfigView(AccumulationRankerConfig config) {
        return new AutoForm(config);
    }

    @Override
    public Indicator<Num> createIndicator(AccumulationRankerConfig config, BarSeries barSeries) {
        return new ADXIndicator(barSeries, config.getBarCount());
    }

    @Override
    public String toString() {
        return "Accumulation";
    }

    @Data
    public static class AccumulationRankerConfig implements RankerConfig {
        private int barCount = 500;
    }

}
