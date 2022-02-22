package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReaccumulationRule extends AbstractRule {

	private final BarSeries barSeries;
	private final ReaccumulationRuleConfig config;
	
	@Data
	public static class ReaccumulationRuleConfig {
		private int barCount = 55;
		private double minUpMovePercentage = 20;
		private double maxUpMovePercentage = 100;
		private double minDownMovePercentage = 10;
		private double maxDownMovePercentage = 66.66;
		private double barCountMultiple = 1.5;
		private double priceMoveMultiple = 2;
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		final int barCount = config.getBarCount();
		if(index < barCount || index >= barSeries.getBarCount()) return false;
		Bar highestHighBar = null, lowestLowBar = null, currentBar = barSeries.getBar(index);
		int highestHighBarIndex = index, lowestLowBarIndex = index;
		for(int barIndex = index; barIndex > index - barCount; barIndex--) {
			final Bar bar = barSeries.getBar(barIndex);
			if(null == highestHighBar || highestHighBar.getHighPrice().isLessThanOrEqual(bar.getHighPrice())) {
				highestHighBar = bar;
				highestHighBarIndex = barIndex;
			}
			if(null == lowestLowBar || lowestLowBar.getLowPrice().isGreaterThanOrEqual(bar.getLowPrice())) {
				lowestLowBar = bar;
				lowestLowBarIndex = barIndex;
			}
		}
		
		if(highestHighBarIndex >= index) return false;
		if(lowestLowBarIndex >= highestHighBarIndex) return false;
		if(null == highestHighBar || null == lowestLowBar) return false;
		if(index - highestHighBarIndex <= highestHighBarIndex - lowestLowBarIndex) return false;
		
		final double highestHigh = highestHighBar.getHighPrice().doubleValue();
		final double lowestLow = lowestLowBar.getLowPrice().doubleValue();
		final double currentHigh = currentBar.getHighPrice().doubleValue();
		final double currentLow = currentBar.getLowPrice().doubleValue();
		
		if(highestHigh <= currentHigh) return false;
		if(highestHigh <= lowestLow) return false;
		if(currentLow <= lowestLow) return false;
		
		final double upMove = highestHigh - lowestLow;
		final double upMovePercentage = upMove * 100 / lowestLow;
		if(upMovePercentage > config.getMaxUpMovePercentage() || upMovePercentage < config.getMinUpMovePercentage()) return false;
		
		final double downMove = highestHigh - currentLow;
		final double downMovePercentage = downMove * 100 / highestHigh;
		if(downMovePercentage > config.getMaxDownMovePercentage() || downMovePercentage < config.getMinDownMovePercentage()) return false;
		
		final double averageUpMove = upMove/(index - highestHighBarIndex);
		final double averageDownMove = downMove / (highestHighBarIndex - lowestLowBarIndex);
		//if(averageUpMove < config.getMultiple() * averageDownMove) return false;
		
		return true;
	}

}
