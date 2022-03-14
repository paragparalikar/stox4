package com.stox.charting.plot;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.chart.Chart;

public class YAxisPlot<T, C, N> extends Plot<T, C, N> {

	private final YAxis yAxis = new YAxis();
	
	public YAxisPlot(Plottable<T, C, N> plottable) {
		super(plottable);
	}
	
	public void setChart(Chart chart) {
		super.setChart(chart);
		yAxis.getMutableHeightProperty().unbind();
		yAxis.getMutableHeightProperty().bind(chart.getYAxis().heightProperty());
	}
	
	protected void updateYAxis(int startIndex, int endIndex) {
		yAxis.reset();
		double lowestValue = yAxis.getLowestValue();
		double highestValue = yAxis.getHighestValue(); 
		for(int index = startIndex; index < endIndex; index++) {
			final T model = getIndicator().getValue(index);
			if(null != model) {
				lowestValue = Math.min(lowestValue, getPlottable().resolveLowValue(model));
				highestValue = Math.max(highestValue, getPlottable().resolveHighValue(model));
			}
		}
		yAxis.setLowestValue(lowestValue);
		yAxis.setHighestValue(highestValue);
	}
	
	@Override
	protected void createUnits(int startIndex, int endIndex, XAxis xAxis, YAxis yAxis, ChartingContext context) {
		super.createUnits(startIndex, endIndex, xAxis, this.yAxis, context);
	}

}
