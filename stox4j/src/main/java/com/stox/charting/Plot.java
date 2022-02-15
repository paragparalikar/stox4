package com.stox.charting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.ta4j.core.Indicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

import com.stox.charting.axis.XAxis;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.resolver.HighLowResolver;
import com.stox.common.util.MathUtil;

import javafx.scene.Group;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Plot<T> extends Group {

	private final Indicator<T> indicator;
	private final Supplier<Unit<T>> unitSupplier;
	private final HighLowResolver<T> highLowResolver;
	private final List<Unit<T>> units = new ArrayList<>();
	private int lastUnitIndex = Integer.MAX_VALUE;
	
	public void layoutChildren(XAxis xAxis, 
			double parentHeight, double parentWidth) {
		
		final int barCount = indicator.getBarSeries().getBarCount();
		final int startIndex = MathUtil.clip(0, xAxis.getStartIndex(), barCount);
		final int endIndex = MathUtil.clip(0, xAxis.getEndIndex(), barCount);
		
		Num lowestValue = DoubleNum.valueOf(Double.MAX_VALUE);
		Num highestValue = DoubleNum.valueOf(Double.MIN_VALUE); 
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
		
		final int visibleBarCount = endIndex - startIndex;
		final int unitLimit = Math.min(units.size(), lastUnitIndex);
		for(int index = startIndex, unitIndex = 0; 
				index < endIndex || unitIndex < unitLimit; 
				index++, unitIndex++) {
			if(index < endIndex) {
				final Unit<T> unit = units.get(unitIndex);
				unit.setVisible(true);
				unit.layoutChildren(index, indicator.getValue(index), 
						xAxis, highestValue, lowestValue, parentHeight);
			} else {
				units.get(unitIndex).setVisible(false);
			}
		}
		lastUnitIndex = visibleBarCount;
	}
	
	@Override
	protected void layoutChildren() {

	}
	
}
