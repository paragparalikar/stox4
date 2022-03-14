package com.stox.ml;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

public class BarValueNormalizer {

	public List<Bar> normalize(List<Bar> bars){
		Num lowestVolume = DoubleNum.valueOf(Double.MAX_VALUE);
		Num highestVolume = DoubleNum.valueOf(Double.MIN_VALUE);
		Num lowestLowPrice = DoubleNum.valueOf(Double.MAX_VALUE);
		Num highestHighPrice = DoubleNum.valueOf(Double.MIN_VALUE);
		
		for(Bar bar : bars) {
			lowestVolume = lowestVolume.min(bar.getVolume());
			highestVolume = highestVolume.max(bar.getVolume());
			lowestLowPrice = lowestLowPrice.min(bar.getLowPrice());
			highestHighPrice = highestHighPrice.max(bar.getHighPrice());
		}
		
		final Duration day = Duration.ofDays(1);
		final List<Bar> results = new ArrayList<>(bars.size());
		for(int index = 0; index < bars.size(); index++) {
			final Bar bar = bars.get(index);
			final Num volumeDenominator = highestVolume.minus(lowestVolume);
			final Num priceDenominator = highestHighPrice.minus(lowestLowPrice);
			final Bar result = BaseBar.builder()
					.openPrice(bar.getOpenPrice().minus(lowestLowPrice).dividedBy(priceDenominator))
					.highPrice(bar.getHighPrice().minus(lowestLowPrice).dividedBy(priceDenominator))
					.lowPrice(bar.getLowPrice().minus(lowestLowPrice).dividedBy(priceDenominator))
					.closePrice(bar.getClosePrice().minus(lowestLowPrice).dividedBy(priceDenominator))
					.volume(bar.getVolume().minus(lowestVolume).dividedBy(volumeDenominator))
					.endTime(bar.getEndTime())
					.timePeriod(day)
					.build();
			results.add(result);
		}
		return results;
	}
	
}
