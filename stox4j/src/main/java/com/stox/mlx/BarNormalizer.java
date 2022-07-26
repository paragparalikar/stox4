package com.stox.mlx;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

public class BarNormalizer implements Consumer<Bar>, UnaryOperator<Bar> {
	
	private double lowestPrice = Double.MAX_VALUE;
	private double highestPrice = Double.MIN_VALUE;
	private double lowestVolume = Double.MAX_VALUE;
	private double highestVolume = Double.MIN_VALUE;
	
	@Override
	public void accept(Bar bar) {
		lowestPrice = Math.min(lowestPrice, bar.getLowPrice().doubleValue());
		highestPrice = Math.max(highestPrice, bar.getHighPrice().doubleValue());
		lowestVolume = Math.min(lowestVolume, bar.getVolume().doubleValue());
		highestVolume = Math.max(highestVolume, bar.getVolume().doubleValue());
	}

	@Override
	public Bar apply(Bar bar) {
		return BaseBar.builder()
				.openPrice(normalizePrice(bar.getOpenPrice()))
				.highPrice(normalizePrice(bar.getHighPrice()))
				.lowPrice(normalizePrice(bar.getLowPrice()))
				.closePrice(normalizePrice(bar.getClosePrice()))
				.volume(normalizeVolume(bar.getVolume()))
				.timePeriod(bar.getTimePeriod())
				.endTime(bar.getEndTime())
				.build();
	}
	
	public Num normalizePrice(Num value) {
		return DoubleNum.valueOf((value.doubleValue() - lowestPrice) / (highestPrice - lowestPrice));
	}
	
	public Num normalizeVolume(Num value) {
		return DoubleNum.valueOf((value.doubleValue() - lowestVolume) / (highestVolume - lowestVolume));
	}
}