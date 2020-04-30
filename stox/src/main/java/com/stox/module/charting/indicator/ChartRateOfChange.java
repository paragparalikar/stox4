package com.stox.module.charting.indicator;


import java.util.ArrayList;
import java.util.List;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.indicator.addin.ChartAddIn;
import com.stox.module.charting.indicator.addin.LevelAddIn;
import com.stox.module.charting.indicator.addin.OverboughtAddIn;
import com.stox.module.charting.indicator.addin.OversoldAddIn;
import com.stox.module.charting.indicator.unit.PointUnit;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.PolylineUnitParent;
import com.stox.module.charting.unit.parent.UnitParent;
import com.stox.module.indicator.Indicator;
import com.stox.module.indicator.RateOfChange;
import com.stox.module.indicator.RateOfChange.Config;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Polyline;
import lombok.experimental.Delegate;

public class ChartRateOfChange  extends AbstractChartIndicator<Config, Double, Point2D> {

	@Delegate
	private final Indicator<Config, Double> indicator = Indicator.ofType(RateOfChange.class);

	@Override
	public UnitParent<Point2D> parent(Group group) {
		final Polyline line = new Polyline();
		group.getChildren().add(line);
		return new PolylineUnitParent(line);
	}

	@Override
	public String name() {
		return "Rate Of Change";
	}

	@Override
	public Unit<Double> unit(Parent<Point2D> parent) {
		return new PointUnit(parent);
	}

	@Override
	public List<ChartAddIn<Double>> addIns(final Config config, final UnitParent<Point2D> parent) {
		final PolylineUnitParent polylineUnitParent = (PolylineUnitParent) parent;
		final ArrayList<ChartAddIn<Double>> addIns = new ArrayList<>(3);
		addIns.add(new LevelAddIn<>(() -> 0d));
		addIns.add(new OverboughtAddIn<>(polylineUnitParent.getNode(), () -> 50d));
		addIns.add(new OversoldAddIn<>(polylineUnitParent.getNode(), () -> -50d));
		return addIns;
	}

	@Override
	public boolean groupable() {
		return true;
	}

	@Override
	public Underlay underlay(Config config) {
		return Underlay.NONE;
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
