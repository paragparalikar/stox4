package com.stox.ranker;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import lombok.Data;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator; 
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.CombineIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

// Long Term Volatility Ranker
public class LongTermVolatilityContractionRanker implements Ranker<LongTermVolatilityContractionRanker.LongTermVolatilityContractionRankerConfig> {

    @Override
    public LongTermVolatilityContractionRankerConfig createConfig() {
        return new LongTermVolatilityContractionRankerConfig();
    }

    @Override
    public Indicator<Num> createIndicator(LongTermVolatilityContractionRankerConfig config, BarSeries barSeries) {
        final ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        final StandardDeviationIndicator standardDeviationIndicator = new StandardDeviationIndicator(closePriceIndicator, config.getBarCount());
        final HighestValueIndicator highestValueIndicator = new HighestValueIndicator(standardDeviationIndicator, config.getBarCount());
        return new CombineIndicator(standardDeviationIndicator, highestValueIndicator, Num::dividedBy);
    }

    @Override
    public ConfigView createConfigView(LongTermVolatilityContractionRankerConfig config) {
        return new AutoForm(config);
    }

    @Override
    public String toString() {
        return "Long Term Volatility Contraction";
    }

    @Data
    public static class LongTermVolatilityContractionRankerConfig implements RankerConfig {
        private int barCount;
    }


}
