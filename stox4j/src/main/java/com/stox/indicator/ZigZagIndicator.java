package com.stox.indicator;

import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;

public class ZigZagIndicator extends AbstractIndicator<Move> {

	private final int barCount;
	private final List<Move> moves;
	private final BarSeries barSeries;
	private final double tolarancePercentage;
	
	public ZigZagIndicator(BarSeries barSeries, int barCount, double tolarancePercentage) {
		super(barSeries);
		this.barCount = barCount;
		this.barSeries = barSeries;
		this.tolarancePercentage = tolarancePercentage;
		this.moves = calculateAll();
	}

	public List<Move> calculateAll(){
		final List<Move> moves = new ArrayList<>();
		if(2 >= barSeries.getBarCount()) return moves;
		boolean up = true, down = true;
		double pointer = barSeries.getLastBar().getClosePrice().doubleValue();
		int pivoteIndex = barSeries.getBarCount() - 1, pointerIndex = pivoteIndex;
		for(int index = barSeries.getBarCount() - 2; index >= 0; index--) {
			final Bar bar = barSeries.getBar(index);
			final double low = bar.getLowPrice().doubleValue();
			final double high = bar.getHighPrice().doubleValue();
			if(up) {
				if(high >= pointer) {
					pointer = high;
					pointerIndex = index;
				}
				if(low <= pointer * (100 - tolarancePercentage) / 100) {
					moves.add(new Move(barSeries.getBarData().subList(pointerIndex, pivoteIndex + 1), 
							up, down,
							pointerIndex, pivoteIndex));
					pivoteIndex = pointerIndex;
					pointerIndex = index;
					pointer = low;
					up = false;
					down = true;
				}
			}
			if(down) {
				if(low <= pointer) {
					pointer = low;
					pointerIndex = index;
				}
				if(high >= pointer * (100 - tolarancePercentage) / 100) {
					moves.add(new Move(barSeries.getBarData().subList(pointerIndex, pivoteIndex + 1), 
							up, down,
							pointerIndex, pivoteIndex));
					pivoteIndex = pointerIndex;
					pointerIndex = index;
					pointer = high;
					up = true;
					down = false;
				}
			}
		}
		return moves;
	}

	@Override
	public Move getValue(int index) {
		for(Move move : moves) {
			if(index == move.getEndIndex()) {
				return move;
			}
		}
		return null;
	}
	
}
