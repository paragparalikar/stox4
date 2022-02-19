package com.stox.charting.plot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.ta4j.core.Indicator;

import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.unit.Unit;
import com.stox.common.util.MathUtil;

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
	private int lastUnitIndex = Integer.MAX_VALUE;
	
	public Plot(Supplier<Unit<T>> unitSupplier) {
		this.unitSupplier = unitSupplier;
		setManaged(false);
		setAutoSizeChildren(false);
	}
	
	public abstract void reload();
	protected abstract double resolveLowValue(T model);
	protected abstract double resolveHighValue(T model);
	
	public void setContext(ChartingContext context) {
		this.context = context;
		context.getBarSeriesProperty().addListener((o,old,value) -> reload());
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
		for(int index = units.size(); index < endIndex - startIndex; index++) {
			final Unit<T> unit = unitSupplier.get();
			unit.setXAxis(xAxis);
			unit.setYAxis(yAxis);
			unit.setContext(context);
			units.add(unit);
			getChildren().add(unit.asNode());
		}
	}
	
	protected void layoutChartChildren(int startIndex, int endIndex) {
		final int unitLimit = Math.min(units.size(), lastUnitIndex);
		for(int index = Math.max(endIndex, unitLimit), unitIndex = 0; 
				index >= startIndex && unitIndex < unitLimit; 
				index--, unitIndex++) {
			final Unit<T> unit = units.get(unitIndex);
			if(index < endIndex) {
				unit.setVisible(true);
				layoutUnit(index, unit, indicator.getValue(index));
			} else {
				unit.setVisible(false);
			}
		}
		lastUnitIndex = endIndex - startIndex;
	}
	
	protected void layoutUnit(int index, Unit<T> unit, T model) {
		unit.layoutChildren(index, model);
	}
	
	public void layoutChartChildren() {
		final int barCount = context.getBarSeriesProperty().get().getBarCount();
		final int startIndex = MathUtil.clip(0, xAxis.getStartIndex(), barCount);
		final int endIndex = MathUtil.clip(0, xAxis.getEndIndex(), barCount);
		updateYAxis(startIndex, endIndex);
		createUnits(startIndex, endIndex);
		layoutChartChildren(startIndex, endIndex);
	}
	
	@Override
	protected void layoutChildren() {

	}
	
}
