package com.stox.charting.plot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.ta4j.core.Indicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.resolver.HighLowResolver;
import com.stox.common.scrip.Scrip;

import javafx.scene.Group;
import lombok.Getter;
import lombok.Setter;

public abstract class Plot<T> extends Group {

	@Getter @Setter private Indicator<T> indicator;
	@Getter private final ChartingContext context;
	private final Supplier<Unit<T>> unitSupplier;
	private final HighLowResolver<T> highLowResolver;
	private final List<Unit<T>> units = new ArrayList<>();
	private int lastUnitIndex = Integer.MAX_VALUE;
	
	public Plot(ChartingContext context, Supplier<Unit<T>> unitSupplier, HighLowResolver<T> highLowResolver) {
		this.context = context;
		this.unitSupplier = unitSupplier;
		this.highLowResolver = highLowResolver;
		setManaged(false);
		setAutoSizeChildren(false);
	}
	
	public abstract boolean reload(Scrip scrip, XAxis xAxis);
	
	public void updateYAxis(int startIndex, int endIndex, YAxis yAxis) {
		Num lowestValue = DoubleNum.valueOf(yAxis.getLowestValue());
		Num highestValue = DoubleNum.valueOf(yAxis.getHighestValue()); 
		for(int index = startIndex; index < endIndex; index++) {
			final T model = indicator.getValue(index);
			lowestValue = lowestValue.min(highLowResolver.resolveLow(model));
			highestValue = highestValue.max(highLowResolver.resolveHigh(model));
			if(index - startIndex >= units.size()) {
				final Unit<T> unit = unitSupplier.get();
				units.add(unit);
				getChildren().add(unit.asNode());
			}
		}
		yAxis.setHighestValue(highestValue.doubleValue());
		yAxis.setLowestValue(lowestValue.doubleValue());
		yAxis.layoutChartChildren();
	}
	
	public void layoutChildren(
			XAxis xAxis, YAxis yAxis, 
			int startIndex, int endIndex,
			double parentHeight, double parentWidth) {
		
		updateYAxis(startIndex, endIndex, yAxis);
		
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
