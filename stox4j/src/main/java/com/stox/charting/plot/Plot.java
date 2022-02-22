package com.stox.charting.plot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.ConstantIndicator;

import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.chart.Chart;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.util.Maths;

import javafx.scene.Group;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Plot<T, C, N> extends Group {

	private Chart chart;
	private Indicator<T> indicator;
	private final C indicatorConfig;
	private final UnitParent<N> unitParent;
	private final Plottable<T, C, N> plottable;
	private final List<Unit<T, N>> units = new ArrayList<>();
	private PlotInfo<T> info = new DefaultPlotInfo<>(this);
	
	public Plot(Plottable<T, C, N> plottable) {
		setManaged(false);
		setAutoSizeChildren(false);
		this.plottable = plottable;
		this.indicatorConfig = plottable.createConfig();
		this.unitParent = plottable.createUnitParent();
		getChildren().add(unitParent.getNode());
	}
	
	public void reload() {
		final BarSeries barSeries = getChart().getChartingView().getContext().getBarSeriesProperty().get();
		if(null != barSeries && 0 < barSeries.getBarCount()) {
			getInfo().setName(plottable.toString());
			setIndicator(plottable.createIndicator(indicatorConfig, barSeries));
		} else {
			getInfo().setName(null);
			setIndicator(new ConstantIndicator<>(barSeries, null));
		}
	}
	
	public void setChart(Chart chart) {
		this.chart = chart;
		chart.getChartingView().getContext().getBarSeriesProperty().addListener((o,old,value) -> reload());
		chart.getChartingView().getCrosshair().getVerticalLine().endXProperty().addListener((o,old,value) -> {
			final int index = chart.getChartingView().getXAxis().getIndex(value.doubleValue());
			getInfo().setValue(indicator.getValue(index));
		});
	}
	
	protected void updateYAxis(int startIndex, int endIndex) {
		final YAxis yAxis = chart.getYAxis();
		double lowestValue = yAxis.getLowestValue();
		double highestValue = yAxis.getHighestValue(); 
		for(int index = startIndex; index < endIndex; index++) {
			final T model = indicator.getValue(index);
			if(null != model) {
				lowestValue = Math.min(lowestValue, plottable.resolveLowValue(model));
				highestValue = Math.max(highestValue, plottable.resolveHighValue(model));
			}
		}
		yAxis.setHighestValue(highestValue);
		yAxis.setLowestValue(lowestValue);
	}
	
	protected void createUnits(int startIndex, int endIndex) {
		final XAxis xAxis = chart.getChartingView().getXAxis();
		final YAxis yAxis = chart.getYAxis();
		final int visibleBarCount = endIndex - startIndex;
		for(int index = units.size(); index < visibleBarCount; index++) {
			final Unit<T, N> unit = plottable.createUnit();
			unit.setXAxis(xAxis);
			unit.setYAxis(yAxis);
			unit.setContext(chart.getChartingView().getContext());
			units.add(unit);
		}
	}
	
	protected void removeUnits(int startIndex, int endIndex) {
		final int visibleBarCount = endIndex - startIndex;
		if(visibleBarCount < units.size()) {
			final Iterator<Unit<T, N>> iterator = units.subList(visibleBarCount, units.size()).iterator();
			while(iterator.hasNext()) {
				final Unit<T, N> unit = iterator.next();
				unitParent.remove(unit.asChild());
				iterator.remove();
			}
		}
	}
	
	protected void layoutChartChildren(final int startIndex, final int endIndex) {
		unitParent.clear();
		unitParent.preLayoutChartChildren();
		for(int index = endIndex; index >= startIndex; index--) {
			final int unitIndex = endIndex - index;
			if(0 <= unitIndex && unitIndex < units.size()) {
				final Unit<T, N> unit = units.get(unitIndex);
				layoutUnit(index, unit, indicator.getValue(index));
			}
		}
		unitParent.postLayoutChartChildren();
	}
	
	protected void layoutUnit(int index, Unit<T, N> unit, T model) {
		unitParent.add(unit.asChild());
		unit.layoutChildren(index, model);
	}
	
	public void layoutChartChildren() {
		final XAxis xAxis = chart.getChartingView().getXAxis();
		final ChartingContext context = chart.getChartingView().getContext();
		final int startIndex = Maths.clip(0, xAxis.getStartIndex(), context.getBarCount() - 1);
		final int endIndex = Maths.clip(0, xAxis.getEndIndex(), context.getBarCount() - 1);
		updateYAxis(startIndex, endIndex);
		createUnits(startIndex, endIndex);
		removeUnits(startIndex, endIndex);
		layoutChartChildren(startIndex, endIndex);
	}
	
	@Override
	protected void layoutChildren() {

	}
	
}
