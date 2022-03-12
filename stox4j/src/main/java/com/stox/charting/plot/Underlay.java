package com.stox.charting.plot;

import com.stox.charting.ChartingView;
import com.stox.charting.chart.Chart;

public enum Underlay {

	PRICE{
		@Override
		public void addPlot(Plottable plottable, ChartingView chartingView) {
			final Chart chart = new Chart(chartingView);
			final Plot plot = new Plot(plottable);
			chartingView.getPriceChart().add(plot);
		}
	}, VOLUME{
		@Override
		public void addPlot(Plottable plottable, ChartingView chartingView) {
			throw new UnsupportedOperationException();
		}
	}, INDEPENDENT{
		@Override
		public void addPlot(Plottable plottable, ChartingView chartingView) {
			final Chart chart = new Chart(chartingView);
			chartingView.add(chart);
			chart.add(new Plot(plottable));
		}
	};
	
	public abstract void addPlot(Plottable plottable, ChartingView chartingView);
	
}
