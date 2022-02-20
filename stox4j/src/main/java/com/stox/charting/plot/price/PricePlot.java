package com.stox.charting.plot.price;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.charting.ChartingView.ChartingConfig;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.crosshair.Crosshair;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.PlotInfo;
import com.stox.charting.unit.CandleUnit;
import com.stox.charting.unit.Unit;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.BarIndicator;

public class PricePlot extends Plot<Bar> {

	private final ChartingConfig config;
	private final BarService barService;
	private volatile boolean fullyLoaded, loading;
	private final PricePlotInfo infoPane = new PricePlotInfo();
	
	public PricePlot(ChartingConfig config, Crosshair crosshair, BarService barService) {
		super(CandleUnit::new);
		this.config = config;
		this.barService = barService;
	}
	
	@Override
	public PlotInfo<Bar> getInfo() {
		return infoPane;
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
			infoPane.set(scrip);
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
			count = Math.max(config.getFetchSize(), xAxis.getEndIndex() - xAxis.getStartIndex());
		} else {
			to = barSeries.getLastBar().getEndTime();
			count = Math.max(config.getFetchSize(), xAxis.getEndIndex() - barSeries.getBarCount());
		}
		final List<Bar> bars = barService.find(scrip.getIsin(), count, to);
		fullyLoaded = bars.isEmpty(); //bars.size() < count;
		final List<Bar> data = barSeries.getBarData();
		data.addAll(bars);
		final BarSeries newBarSeries = new BaseBarSeries(data);
		getContext().getBarSeriesProperty().set(newBarSeries);
		if(!fullyLoaded && xAxis.getEndIndex() > data.size()) doReload(scrip);
	}

	@Override
	public void layoutChartChildren() {
		getXAxis().resetLayout();
		super.layoutChartChildren();
	}
	
	@Override
	protected void layoutUnit(int index, Unit<Bar> unit, Bar model) {
		super.layoutUnit(index, unit, model);
		getXAxis().layoutUnit(index, model);
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
