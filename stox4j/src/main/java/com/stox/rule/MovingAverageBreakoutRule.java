package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

import com.stox.common.bar.BarValueType;
import com.stox.screener.ScreenerConfig;

import lombok.Data;

public class MovingAverageBreakoutRule extends AbstractRule {

	private final BarSeries series;
	private final Indicator<Num> smaIndicator;
	private final MovingAverageBreakoutRuleConfig config;
	
	@Data
	public static class MovingAverageBreakoutRuleConfig implements ScreenerConfig {
		private int barCount = 50;
		private BarValueType barValueType = BarValueType.CLOSE;
		public String toString() { return "barCount = " + barCount; }
	}
	
	public MovingAverageBreakoutRule(BarSeries series, MovingAverageBreakoutRuleConfig config) {
		this.config = config;
		this.series = series;
		final Indicator<Num> indicator = config.getBarValueType().createIndicator(series);
		this.smaIndicator = new SMAIndicator(indicator, config.getBarCount());
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(null != series && index < series.getBarCount() && index >= config.getBarCount()) {
			final Num smaValue = smaIndicator.getValue(index);
			if(null != smaValue && !smaValue.isNaN() && !smaValue.isZero()) {
				final Bar bar = series.getBar(index);
				final Bar one = series.getBar(index - 1);
				
				if(bar.getClosePrice().isLessThan(smaValue)) return false;
				
				if(bar.getClosePrice().isLessThan(one.getHighPrice())) return false;
				
				final Num lower = bar.getLowPrice().min(one.getLowPrice());
				if(lower.isGreaterThan(smaValue)) return false;
				
				return true;
			}
		}
		return false;
	}

}
