package com.stox.module.charting.indicator;

import java.util.Collections;
import java.util.List;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.indicator.addin.ChartAddIn;
import com.stox.module.charting.indicator.unit.PointUnit;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.PolylineUnitParent;
import com.stox.module.charting.unit.parent.UnitParent;
import com.stox.module.indicator.ExponentialMovingAverage;
import com.stox.module.indicator.ExponentialMovingAverage.Config;
import com.stox.module.indicator.Indicator;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Polyline;
import lombok.experimental.Delegate;

public class ChartExponentialMovingAverage extends AbstractChartIndicator<Config, Double, Point2D> {
	
	@Delegate
	private final ExponentialMovingAverage indicator = Indicator.ofType(ExponentialMovingAverage.class);

	@Override
	public String name() {
		return "Exponential Moving Average";
	}

	@Override
	public UnitParent<Point2D> parent(Group group) {
		final Polyline line = new Polyline();
		group.getChildren().add(line);
		return new PolylineUnitParent(line);
	}

	@Override
	public Unit<Double> unit(Parent<Point2D> parent) {
		return new PointUnit(parent);
	}

	@Override
	public boolean groupable() {
		return true;
	}

	@Override
	public Underlay underlay(final Config config) {
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
	public List<ChartAddIn<Double>> addIns(Config config, UnitParent<Point2D> parent) {
		return Collections.emptyList();
	}

	@Override
	public double min(Double value) {
		return null == value ? Double.MAX_VALUE : value;
	}

	@Override
	public double max(Double value) {
		return null == value ? Double.MIN_VALUE : value;
	}

}
