package com.stox.charting.plot;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.handler.pan.PanRequestEvent;
import com.stox.charting.handler.zoom.ZoomRequestEvent;
import com.stox.charting.unit.CandleUnit;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.indicator.BarIndicator;

import javafx.application.Platform;

public class PricePlot extends Plot<Bar> {
	private static final int FETCH_SIZE = 200;

	private final BarService barService;
	private final ExecutorService executor;
	private volatile boolean fullyLoaded, loading;
	
	public PricePlot(BarService barService, ExecutorService executor) {
		super(CandleUnit::new);
		this.barService = barService;
		this.executor = executor;
		addEventHandler(PanRequestEvent.TYPE, event -> reloadBars());
		addEventHandler(ZoomRequestEvent.TYPE, event -> reloadBars());
	}

	@Override
	public void setContext(ChartingContext context) {
		super.setContext(context);
		context.getScripProperty().addListener((o,old,scrip) -> {
			loading = false;
			fullyLoaded = false;
			reloadBars();
		});
	}
	
	@Override
	public void reload() {
		final BarSeries barSeries = getContext().getBarSeriesProperty().get();
		if(null != barSeries) setIndicator(new BarIndicator(barSeries));
		if(Platform.isFxApplicationThread()) {
			layoutChartChildren();
		} else {
			Platform.runLater(this::layoutChartChildren);
		}
	}
	
	public void reloadBars() {
		reload();
		executor.submit(this::doReload);
	}

	private void doReload() {
		try {
			final Scrip scrip = getContext().getScripProperty().get();
			if(null != scrip && !loading && !fullyLoaded) {
				loading = true;
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
				fullyLoaded = bars.size() < count;
				final List<Bar> data = barSeries.getBarData();
				data.addAll(bars);
				final BarSeries newBarSeries = new BaseBarSeries(data);
				getContext().getBarSeriesProperty().set(newBarSeries);
				if(!fullyLoaded && xAxis.getEndIndex() > data.size()) {
					executor.submit(this::doReload);
				};
			}
		} finally {
			loading = false;
		}
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
