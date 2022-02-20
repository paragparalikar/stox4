package com.stox.charting.plot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import org.ta4j.core.Indicator;

import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.crosshair.Crosshair;
import com.stox.charting.unit.Unit;
import com.stox.common.util.Maths;

import javafx.scene.Group;
import lombok.Getter;
import lombok.Setter;

public abstract class Plot<T> extends Group {

	@Setter @Getter private YAxis yAxis;
	@Setter @Getter private XAxis xAxis;
	@Setter @Getter private Indicator<T> indicator;
	@Getter private ChartingContext context;
	private final Supplier<Unit<T>> unitSupplier;
	private final List<Unit<T>> units = new ArrayList<>();
	
	public Plot(Supplier<Unit<T>> unitSupplier) {
		this.unitSupplier = unitSupplier;
		setManaged(false);
		setAutoSizeChildren(false);
	}
	
	public abstract void reload();
	public abstract PlotInfo<T> getInfo();
	protected abstract double resolveLowValue(T model);
	protected abstract double resolveHighValue(T model);
	
	public void setContext(ChartingContext context) {
		this.context = context;
		context.getBarSeriesProperty().addListener((o,old,value) -> reload());
	}
	
	public void setCrosshair(Crosshair crosshair) {
		crosshair.getVerticalLine().endXProperty().addListener((o,old,value) -> {
			final int index = getXAxis().getIndex(value.doubleValue());
			getInfo().setValue(indicator.getValue(index));
		});
	}
	
	protected void updateYAxis(int startIndex, int endIndex) {
		double lowestValue = yAxis.getLowestValue();
		double highestValue = yAxis.getHighestValue(); 
		for(int index = startIndex; index < endIndex; index++) {
			final T model = indicator.getValue(index);
			lowestValue = Math.min(lowestValue, resolveLowValue(model));
			highestValue = Math.max(highestValue, resolveHighValue(model));
		}
		yAxis.setHighestValue(highestValue);
		yAxis.setLowestValue(lowestValue);
	}
	
	protected void createUnits(int startIndex, int endIndex) {
		final int visibleBarCount = endIndex - startIndex;
		for(int index = units.size(); index < visibleBarCount; index++) {
			final Unit<T> unit = unitSupplier.get();
			unit.setXAxis(xAxis);
			unit.setYAxis(yAxis);
			unit.setContext(context);
			units.add(unit);
			getChildren().add(unit.asNode());
		}
	}
	
	protected void removeUnits(int startIndex, int endIndex) {
		final int visibleBarCount = endIndex - startIndex;
		if(visibleBarCount < units.size()) {
			final Iterator<Unit<T>> iterator = units.subList(visibleBarCount, units.size()).iterator();
			while(iterator.hasNext()) {
				final Unit<T> unit = iterator.next();
				getChildren().remove(unit.asNode());
				iterator.remove();
			}
		}
	}
	
	protected void layoutChartChildren(final int startIndex, final int endIndex) {
		for(int index = endIndex; index > startIndex; index--) {
			final int unitIndex = endIndex - index;
			if(0 <= unitIndex && unitIndex < units.size()) {
				final Unit<T> unit = units.get(unitIndex);
				layoutUnit(index, unit, indicator.getValue(index));
			}
		}
	}
	
	protected void layoutUnit(int index, Unit<T> unit, T model) {
		unit.layoutChildren(index, model);
	}
	
	public void layoutChartChildren() {
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
