package com.stox.module.charting.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.indicator.addin.ChartAddIn;
import com.stox.module.charting.indicator.unit.MoveUnit;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.PolylineUnitParent;
import com.stox.module.charting.unit.parent.UnitParent;
import com.stox.module.core.model.Move;
import com.stox.module.indicator.Indicator;
import com.stox.module.indicator.ZigZagIndicator;
import com.stox.module.indicator.ZigZagIndicator.Config;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Polyline;
import lombok.experimental.Delegate;

public class ChartZigZagIndicator extends AbstractChartIndicator<Config, Move, Point2D> {

	@Delegate
	private final Indicator<Config, Move> indicator = Indicator.ofType(ZigZagIndicator.class);

	@Override
	public UnitParent<Point2D> parent(Group group) {
		final Polyline line = new Polyline();
		group.getChildren().add(line);
		return new PolylineUnitParent(line);
	}

	@Override
	public Unit<Move> unit(Parent<Point2D> parent) {
		return new MoveUnit(parent);
	}

	@Override
	public boolean groupable() {
		return false;
	}

	@Override
	public Underlay underlay(Config config) {
		switch (config.getBarValue()) {
		case OPEN:
		case CLOSE:
		case HIGH:
		case LOW:
		case MID:
			return Underlay.PRICE;
		case SPREAD:
			return Underlay.NONE;
		case VOLUME:
			return Underlay.VOLUME;
		default:
			throw new IllegalArgumentException("Underlay " + config.getBarValue().name() + " is not supported.");
		}
	}

	@Override
	public List<ChartAddIn<Move>> addIns(Config config, UnitParent<Point2D> parent) {
		return Collections.emptyList();
	}

	@Override
	public double min(Move value) {
		return null == value ? Double.MAX_VALUE : Math.min(value.start().low(), value.end().low());
	}

	@Override
	public double max(Move value) {
		return null == value ? Double.MIN_VALUE : Math.max(value.start().high(), value.end().high());
	}

	@Override
	public String name() {
		return "ZigZag";
	}

	@Override
	public void layoutChartChildren(XAxis xAxis, YAxis yAxis, List<Move> models, List<Unit<Move>> units,
			UnitParent<Point2D> parent, IndicatorPlot<Config, Move, Point2D> plot) {
		final int clippedStartIndex = xAxis.getClippedStartIndex();
		final int clippedEndIndex = xAxis.getClippedEndIndex();
		int unitIndex = -1;
		for (int index = models.size() - 1; index >= 0; index--) {
			final Move move = models.get(index);
			if (clippedStartIndex <= move.startIndex() && move.endIndex() <= clippedEndIndex) {
				final Unit<Move> unit = getUnit(units, ++unitIndex, parent);
				unit.update(unitIndex, move, null, xAxis, yAxis);
			}
		}
		clear(units, Math.max(0, unitIndex));
	}

	private void clear(final List<Unit<Move>> units, final int index) {
		final List<Unit<Move>> removableUnits = new ArrayList<>();
		for (int unitIndex = index; unitIndex < units.size(); unitIndex++) {
			final Unit<Move> unit = units.get(unitIndex);
			unit.detach();
			removableUnits.add(unit);
		}
		units.removeAll(removableUnits);
	}

	private Unit<Move> getUnit(final List<Unit<Move>> units, final int index, final UnitParent<Point2D> parent) {
		if (index >= units.size()) {
			final Unit<Move> unit = unit(parent);
			units.add(unit);
			unit.attach();
			return unit;
		} else {
			return units.get(index);
		}
	}

}
