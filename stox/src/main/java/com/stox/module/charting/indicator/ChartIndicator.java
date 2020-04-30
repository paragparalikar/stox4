package com.stox.module.charting.indicator;

import java.util.Arrays;
import java.util.List;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.indicator.addin.ChartAddIn;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.UnitParent;
import com.stox.module.indicator.Indicator;

import javafx.scene.Group;

public interface ChartIndicator<T, V, P> extends Indicator<T, V> {
	public static final List<ChartIndicator<?,?,?>> ALL = Arrays.asList(
			new ChartSimpleMovingAverage(), new ChartRelativeStrengthIndex(), new ChartBollingerBands());
	
	String name();

	UnitParent<P> parent(final Group group);

	Unit<V> unit(final Parent<P> parent);

	boolean groupable();

	Underlay underlay(final T config);

	List<ChartAddIn<V>> addIns(final T config, final UnitParent<P> parent);

	double min(V value);

	double max(V value);

	void layoutChartChildren(XAxis xAxis, YAxis yAxis, List<V> models, List<Unit<V>> units, UnitParent<P> parent,
			IndicatorPlot<T, V, P> plot);

}
