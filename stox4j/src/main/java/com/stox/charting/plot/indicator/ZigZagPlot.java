package com.stox.charting.plot.indicator;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.indicator.PlottableZigZagIndicator.ZigZagConfig;
import com.stox.charting.unit.Unit;
import com.stox.indicator.Move;

import javafx.geometry.Point2D;

public class ZigZagPlot extends Plot<Move, ZigZagConfig, Point2D> {

	public ZigZagPlot(PlottableZigZagIndicator plottable) {
		super(plottable);
	}
	
	@Override protected void updateYAxis(int startIndex, int endIndex) {}
	@Override protected void createUnits(int startIndex, int endIndex, XAxis xAxis, YAxis yAxis, ChartingContext context) {}
	@Override protected void removeUnits(int startIndex, int endIndex) {}
	
	@Override
	protected void layoutChartChildren(int startIndex, int endIndex) {
		getUnitParent().clear();
		getUnitParent().preLayoutChartChildren();
		for(int index = endIndex; index >= startIndex; index--) {
			final Move model = getIndicator().getValue(index);
			if(null != model) {
				final Unit<Move, Point2D> unit = getPlottable().createUnit();
				unit.setXAxis(getChart().getChartingView().getXAxis());
				unit.setYAxis(getChart().getYAxis());
				layoutUnit(index, unit, model);
			}
		}
		getUnitParent().postLayoutChartChildren();
	}

}
