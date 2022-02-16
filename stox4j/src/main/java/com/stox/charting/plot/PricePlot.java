package com.stox.charting.plot;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.google.common.base.Objects;
import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.unit.CandleUnit;
import com.stox.charting.unit.resolver.BarHighLowResolver;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.BarIndicator;

public class PricePlot extends Plot<Bar> {
	private static final int INITIAL_BAR_COUNT = 200;
	private static final int FETCH_BAR_COUNT = 100;

	private Scrip scrip;
	private final BarService barService;
	
	public PricePlot(ChartingContext context, BarService barService) {
		super(context, CandleUnit::new, new BarHighLowResolver());
		this.barService = barService;
	}

	@Override
	public void reload(Scrip scrip, XAxis xAxis) {
		if(null == getIndicator() || !Objects.equal(scrip, this.scrip)) {
			final List<Bar> bars = barService.find(scrip.getIsin(), INITIAL_BAR_COUNT);
			final BarSeries barSeries = new BaseBarSeries(bars);
			setIndicator(new BarIndicator(barSeries));
			getContext().setBarSeries(barSeries);
			this.scrip = scrip;
		} else {
			final BarSeries barSeries = getIndicator().getBarSeries();
			final int barCount = barSeries.getBarCount();
			if(xAxis.getEndIndex() > barCount) {
				final int gap = xAxis.getEndIndex() - barCount;
				final int count = Math.max(gap, 0 == barCount ? INITIAL_BAR_COUNT : FETCH_BAR_COUNT);
				final ZonedDateTime to = 0 == barCount ? ZonedDateTime.now() : barSeries.getLastBar().getEndTime();
				final List<Bar> bars = barService.find(scrip.getIsin(), count, to);
				final List<Bar> data = barSeries.getBarData();
				data.addAll(bars);
				final BarSeries newBarSeries = new BaseBarSeries(data);
				setIndicator(new BarIndicator(newBarSeries));
				getContext().setBarSeries(newBarSeries);
			}
		}
	}

	@Override
	public void layoutChildren(XAxis xAxis, double parentHeight, double parentWidth) {
		super.layoutChildren(xAxis, parentHeight, parentWidth);
		xAxis.layoutChartChildren(getContext().getBarSeries());
	}
	
}
