package com.stox.module.charting.plot;

import java.util.ArrayList;
import java.util.List;

import com.stox.fx.fluent.scene.layout.FluentGroup;
import com.stox.module.charting.Attachable;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.plot.info.PlotInfoPane;
import com.stox.module.charting.plot.info.SimplePlotInfoPane;
import com.stox.module.charting.unit.Unit;
import com.stox.util.MathUtil;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public abstract class Plot<T>{

	private final Configuration configuration;
	private final List<T> models = new ArrayList<>();
	private final List<Unit<T>> units = new ArrayList<>();
	private double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
	private final PlotInfoPane plotInfoPane = buildPlotInfoPane();
	private final ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>(Color.BLUE);
	private final FluentGroup container = new FluentGroup().managed(false).autoSizeChildren(false).classes("plot");

	public abstract Unit<T> unit();

	public abstract double min(final T model);

	public abstract double max(final T model);
	
	public abstract void showIndexInfo(final int index);
	
	protected PlotInfoPane buildPlotInfoPane() {
		return new SimplePlotInfoPane();
	}
	
	public Plot<T> updateValueBounds(final int start, final int end) {
		synchronized(models){
			min = Double.MAX_VALUE;
			max = Double.MIN_VALUE;
			final int clippedEndIndex = MathUtil.clip(0, end, models.size());
			final int clippedStartIndex = MathUtil.clip(0, start, models.size() - 1);
			for (int index = clippedStartIndex; index < clippedEndIndex; index++) {
				final T model = models.get(index);
				min = Math.min(min, min(model));
				max = Math.max(max, max(model));
			}
			return this;
		}
	}

	protected synchronized Plot<T> ensureUnitsSize(final int startIndex, final int endIndex) {
		final int delta = Math.max(0, endIndex) - Math.max(0, startIndex) + 1;
		Attachable.ensureSize(units, delta, this::unit);
		return this;
	}

	public Plot<T> reset() {
		synchronized (models) {
			models.clear();
			showIndexInfo(-1);
			return this;
		}
	}

	public Plot<T> layoutChartChildren(final XAxis xAxis, final YAxis yAxis) {
		synchronized (models) {
			if (!models.isEmpty()) {
				int unitIndex = 0;
				final int end = xAxis.getClippedEndIndex();
				final int start = xAxis.getClippedStartIndex();
				ensureUnitsSize(start, end);
				for (int index = start; index <= end; index++) {
					final T model = models.get(index);
					if (null != model) {
						final Unit<T> unit = units.get(unitIndex++);
						final T previousModel = index < models.size() - 1 ? models.get(index + 1) : null;
						layoutUnit(unit, index, model, previousModel, xAxis, yAxis);
					}
				}
			}
			return this;
		}
	}

	protected Plot<T> layoutUnit(final Unit<T> unit, final int index, final T model, final T previousModel, final XAxis xAxis, final YAxis yAxis) {
		unit.update(index, model, previousModel, xAxis, yAxis);
		return this;
	}
	
}
