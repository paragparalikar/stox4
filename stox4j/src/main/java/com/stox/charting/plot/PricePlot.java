package com.stox.charting.plot;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.unit.CandleUnit;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.BarIndicator;

public class PricePlot extends Plot<Bar> {
	private static final int INITIAL_BAR_COUNT = 200;
	private static final int FETCH_BAR_COUNT = 100;

	private final BarService barService;
	
	public PricePlot(ChartingContext context, BarService barService) {
		super(context, CandleUnit::new);
		this.barService = barService;
	}

	@Override
	public boolean load(XAxis xAxis) {
		final Scrip scrip = getContext().getScrip();
		if(null != scrip && null == getIndicator()) {
			final List<Bar> bars = barService.find(scrip.getIsin(), INITIAL_BAR_COUNT);
			final BarSeries barSeries = new BaseBarSeries(bars);
			setIndicator(new BarIndicator(barSeries));
			getContext().setBarSeries(barSeries);
			return true;
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
				return true;
			}
		}
		return false;
	}

	@Override
	public void layoutChartChildren(
			XAxis xAxis, YAxis yAxis, 
			int startIndex, int endIndex,
			double parentHeight, double parentWidth) {
		super.layoutChartChildren(xAxis, yAxis, startIndex, endIndex, parentHeight, parentWidth);
		xAxis.layoutChartChildren(getContext().getBarSeries());
	}

	@Override
	protected double resolveLowValue(Bar model) {
		return model.getLowPrice().doubleValue();
	}

	@Override
	protected double resolveHighValue(Bar model) {
		return model.getHighPrice().doubleValue();
	}
	
}
