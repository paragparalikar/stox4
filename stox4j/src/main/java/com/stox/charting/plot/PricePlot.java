package com.stox.charting.plot;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.unit.CandleUnit;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.BarIndicator;

public class PricePlot extends Plot<Bar> {
	private static final int FETCH_SIZE = 200;

	private final BarService barService;
	private volatile boolean fullyLoaded, loading;
	
	public PricePlot(BarService barService) {
		super(CandleUnit::new);
		this.barService = barService;
	}

	@Override
	public void setContext(ChartingContext context) {
		super.setContext(context);
		context.getScripProperty().addListener((o,old,scrip) -> {
			loading = false;
			fullyLoaded = false;
			context.getBarSeriesProperty().set(new BaseBarSeries());
			reloadBars();
		});
	}
	
	@Override
	public void reload() {
		final BarSeries barSeries = getContext().getBarSeriesProperty().get();
		if(null != barSeries) setIndicator(new BarIndicator(barSeries));
	}
	
	public void reloadBars() {
		try {
			final Scrip scrip = getContext().getScripProperty().get();
			if(null != scrip && !loading && !fullyLoaded) {
				loading = true;
				doReload(scrip);
			}
		} finally {
			loading = false;
		}
	}

	private void doReload(Scrip scrip) {
		int count = 0;
		ZonedDateTime to = null;
		final XAxis xAxis = getXAxis();
		final BarSeries barSeries = getContext().getBarSeriesProperty().get(); 
		if(null == barSeries || 0 == barSeries.getBarCount()) {
			to = ZonedDateTime.now().plusDays(1);
			count = Math.max(FETCH_SIZE, xAxis.getEndIndex() - xAxis.getStartIndex());
		} else {
			to = barSeries.getLastBar().getEndTime();
			count = Math.max(FETCH_SIZE, xAxis.getEndIndex() - barSeries.getBarCount());
		}
		final List<Bar> bars = barService.find(scrip.getIsin(), count, to);
		fullyLoaded = bars.isEmpty(); //bars.size() < count;
		final List<Bar> data = barSeries.getBarData();
		data.addAll(bars);
		final BarSeries newBarSeries = new BaseBarSeries(data);
		getContext().getBarSeriesProperty().set(newBarSeries);
	}

	@Override
	public void layoutChartChildren() {
		super.layoutChartChildren();
		getXAxis().layoutChartChildren();
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
