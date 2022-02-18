package com.stox.charting.plot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.ta4j.core.Indicator;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.unit.Unit;

import javafx.scene.Group;
import lombok.Getter;
import lombok.Setter;

public abstract class Plot<T> extends Group {

	@Getter @Setter private Indicator<T> indicator;
	@Getter private final ChartingContext context;
	private final Supplier<Unit<T>> unitSupplier;
	private final List<Unit<T>> units = new ArrayList<>();
	private int lastUnitIndex = Integer.MAX_VALUE;
	
	public Plot(ChartingContext context, Supplier<Unit<T>> unitSupplier) {
		this.context = context;
		this.unitSupplier = unitSupplier;
		setManaged(false);
		setAutoSizeChildren(false);
	}
	
	public abstract boolean load(XAxis xAxis);
	protected abstract double resolveLowValue(T model);
	protected abstract double resolveHighValue(T model);
	
	public void updateYAxis(int startIndex, int endIndex, YAxis yAxis) {
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
	
	public void layoutChartChildren(
			XAxis xAxis, YAxis yAxis, 
			int startIndex, int endIndex,
			double parentHeight, double parentWidth) {
		
		updateYAxis(startIndex, endIndex, yAxis);
		
		for(int index = units.size(); index < endIndex - startIndex; index++) {
			final Unit<T> unit = unitSupplier.get();
			units.add(unit);
			getChildren().add(unit.asNode());
		}
		
		final int unitLimit = Math.min(units.size(), lastUnitIndex);
		for(int index = startIndex, unitIndex = 0; 
				index < endIndex || unitIndex < unitLimit; 
				index++, unitIndex++) {
			if(index < endIndex) {
				layoutUnit(index, unitIndex, xAxis, yAxis);
			} else {
				units.get(unitIndex).setVisible(false);
			}
		}
		lastUnitIndex = endIndex - startIndex;
	}
	
	public void layoutUnit(int index, int unitIndex, XAxis xAxis, YAxis yAxis) {
		final Unit<T> unit = units.get(unitIndex);
		unit.setVisible(true);
		unit.layoutChildren(index, indicator.getValue(index), xAxis, yAxis);
	}
	
	@Override
	protected void layoutChildren() {

	}
	
}
