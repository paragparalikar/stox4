package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import com.stox.screener.ScreenerConfig;
import com.stox.screener.ScreenerConfig.FixedBarCountScreenerConfig;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleBreakoutBarRule extends AbstractRule {

	@NonNull private final BarSeries barSeries;
	@Getter private final ScreenerConfig config = new FixedBarCountScreenerConfig(2);
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(null != barSeries && 2 < barSeries.getBarCount()) {
			return match(index) && !match(index - 1);
		}
		return false;
	}
	
	private boolean match(int index) {
		final Bar bar = barSeries.getBar(index);
		final Bar one = barSeries.getBar(index - 1);
		
		// Current bar volume should be more than previous bar volume
		//if(bar.getVolume().isLessThan(one.getVolume())) return false;
		
		// Current bar close should be higher than previous bar close
		if(bar.getClosePrice().isLessThan(one.getHighPrice())) return false;
		
		// Current bar spread should be more than previous bar spread
		//if(bar.getHighPrice().minus(bar.getLowPrice()).isLessThan(one.getHighPrice().minus(one.getLowPrice()))) return false;
		
		return true;
	}

}
