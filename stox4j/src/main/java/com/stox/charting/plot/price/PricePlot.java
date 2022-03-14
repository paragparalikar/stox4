package com.stox.charting.plot.price;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.charting.ChartingConfig;
import com.stox.charting.ChartingContext;
import com.stox.charting.ChartingInput;
import com.stox.charting.ChartingView;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.chart.Chart;
import com.stox.charting.plot.Plot;
import com.stox.charting.unit.Unit;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;

import javafx.scene.Node;

public class PricePlot extends Plot<Bar, Void, Node> {

	private final BarService barService;
	private volatile boolean fullyLoaded, loading;
	private final PricePlotInfo pricePlotInfo = new PricePlotInfo(this);
	
	public PricePlot(BarService barService) {
		super(new PlottableBarIndicator());
		this.barService = barService;
		setInfo(pricePlotInfo);
	}
	
	public void reload() {
		super.reload();
		final Scrip scrip = getChart().getChartingView().getContext().getInputProperty().get().getScrip();
		pricePlotInfo.setName(null == scrip ? null : scrip.getName());
	}
	
	@Override
	public void setChart(Chart chart) {
		super.setChart(chart);
		final ChartingView chartingView = chart.getChartingView();
		final ChartingContext context = chartingView.getContext();
		context.getInputProperty().addListener((o,old,scrip) -> {
			loading = false;
			fullyLoaded = false;
			context.getBarSeriesProperty().set(new BaseBarSeries());
			reloadBars();
		});
	}
	
	public void reloadBars() {
		try {
			final ChartingInput input = getChart().getChartingView().getContext().getInputProperty().get();
			final Scrip scrip = null == input ? null : input.getScrip();
			pricePlotInfo.setName(null == scrip ? null : scrip.getName());
			if(null != scrip && !loading && !fullyLoaded) {
				loading = true;
				doReload(input);
			}
		} finally {
			loading = false;
		}
	}

	private void doReload(ChartingInput input) {
		int count = 0;
		ZonedDateTime to = input.getTo();
		final XAxis xAxis = getChart().getChartingView().getXAxis();
		final ChartingConfig config = getChart().getChartingView().getConfig();
		final ChartingContext context = getChart().getChartingView().getContext();
		final BarSeries barSeries = context.getBarSeriesProperty().get(); 
		if(xAxis.getStartIndex() > 0) {
			return;
		} else if(null == barSeries || 0 == barSeries.getBarCount()) {
			to = Optional.ofNullable(input.getTo()).orElse(ZonedDateTime.now().plusDays(1));
			count = Math.max(config.getFetchSize(), xAxis.getEndIndex() - xAxis.getStartIndex());
		} else {
			to = barSeries.getFirstBar().getEndTime();
			count = Math.max(config.getFetchSize(), xAxis.getEndIndex() - barSeries.getBarCount());
		}
		final List<Bar> bars = barService.find(input.getScrip().getIsin(), count, to);
		fullyLoaded = bars.isEmpty(); //bars.size() < count;
		xAxis.shift(bars.size());
		bars.addAll(barSeries.getBarData());
		final BarSeries newBarSeries = new BaseBarSeries(bars);
		context.getBarSeriesProperty().set(newBarSeries);
	}

	@Override
	public void layoutChartChildren() {
		getChart().getChartingView().getXAxis().resetLayout();
		super.layoutChartChildren();
	}
	
	@Override
	protected void layoutUnit(int index, Unit<Bar, Node> unit, Bar model) {
		super.layoutUnit(index, unit, model);
		getChart().getChartingView().getXAxis().layoutUnit(index, model);
	}
	
}
