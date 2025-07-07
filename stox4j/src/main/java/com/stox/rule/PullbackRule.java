package com.stox.rule;

import com.stox.screener.ScreenerConfig;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

@RequiredArgsConstructor
public class PullbackRule extends AbstractRule {

    @Data
    public static class PullbackRuleConfig implements ScreenerConfig {
        private int barCount = 21;
        private double minMultiple = 1.5;
    }

    private final BarSeries barSeries;
    private final PullbackRuleConfig config;

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        final int barCount = config.getBarCount();
        if(index < barCount || index >= barSeries.getBarCount()) return false;
        int highestHighBarIndex = index, lowestLowBarIndex = index;
        double highestHighValue = Double.MIN_VALUE, lowestLowValue = Double.MAX_VALUE;
        for(int barIndex = index; barIndex > index - barCount; barIndex--) {
            final Bar bar = barSeries.getBar(barIndex);
            if(highestHighValue < bar.getHighPrice().doubleValue()) {
                highestHighValue = bar.getHighPrice().doubleValue();
                highestHighBarIndex = barIndex;
            }
            if(lowestLowValue > bar.getLowPrice().doubleValue()) {
                lowestLowValue = bar.getLowPrice().doubleValue();
                lowestLowBarIndex = barIndex;
            }
        }

        if(highestHighBarIndex >= index) return false;
        if(lowestLowBarIndex >= highestHighBarIndex) return false;

        final double currentCloseValue = barSeries.getBar(index).getClosePrice().doubleValue();
        if(highestHighValue <= currentCloseValue) return false;
        if(lowestLowValue >= currentCloseValue) return false;

        final double upPricePerBar = (highestHighValue - lowestLowValue) / (highestHighBarIndex - lowestLowBarIndex);
        final double downPricePerBar = (highestHighValue - currentCloseValue) / (index - highestHighBarIndex);
        if(config.getMinMultiple() > upPricePerBar / downPricePerBar) return false;

        return true;
    }

}
