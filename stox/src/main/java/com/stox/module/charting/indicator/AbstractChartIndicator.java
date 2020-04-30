package com.stox.module.charting.indicator;

import java.util.List;

import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.UnitParent;

public abstract class AbstractChartIndicator<T, V, P> implements ChartIndicator<T, V, P> {

	@Override
	public void layoutChartChildren(
			XAxis xAxis, 
			YAxis yAxis, 
			List<V> models, 
			List<Unit<V>> units, 
			UnitParent<P> parent,
			IndicatorPlot<T, V, P> plot) {
		plot.doLayoutChartChildren(xAxis, yAxis);
	}
	
	@Override
	public String toString() {
		return name();
	}

}
