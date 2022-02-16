package com.stox.charting.price;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.charting.Plot;
import com.stox.charting.axis.XAxis;
import com.stox.charting.unit.CandleUnit;
import com.stox.charting.unit.resolver.BarHighLowResolver;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.BarIndicator;

public class PricePlot extends Plot<Bar> {
	private static final int INITIAL_BAR_COUNT = 200;
	private static final int FETCH_BAR_COUNT = 100;

	private final BarService barService;
	private final BarIndicator barIndicator;
	
	public PricePlot(BarIndicator barIndicator, BarService barService) {
		super(barIndicator, CandleUnit::new, new BarHighLowResolver());
		this.barIndicator = barIndicator;
		this.barService = barService;
	}

	@Override
	public void reload(Scrip scrip, XAxis xAxis) {
		final BarSeries barSeries = barIndicator.getBarSeries();
		final int barCount = barSeries.getBarCount();
		if(xAxis.getEndIndex() > barCount) {
			final int gap = xAxis.getEndIndex() - barCount;
			final int count = Math.max(gap, 0 == barCount ? INITIAL_BAR_COUNT : FETCH_BAR_COUNT);
			final ZonedDateTime to = 0 == barCount ? ZonedDateTime.now() : barSeries.getLastBar().getEndTime();
			final List<Bar> bars = barService.find(scrip.getIsin(), count, to);
			final List<Bar> data = barSeries.getBarData();
			data.addAll(bars);
			barIndicator.setBarSeries(new BaseBarSeries(data));
		}
	}

	@Override
	public void layoutChildren(XAxis xAxis, double parentHeight, double parentWidth) {
		super.layoutChildren(xAxis, parentHeight, parentWidth);
		xAxis.layoutChartChildren(barIndicator.getBarSeries());
	}
	
}
