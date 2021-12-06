package com.stox.module.charting.indicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.indicator.addin.ChartAddIn;
import com.stox.module.charting.indicator.addin.LevelAddIn;
import com.stox.module.charting.indicator.addin.OverboughtAddIn;
import com.stox.module.charting.indicator.addin.OversoldAddIn;
import com.stox.module.charting.indicator.unit.MultiValueUnit;
import com.stox.module.charting.indicator.unit.parent.MultiValueUnitParent;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.UnitParent;
import com.stox.module.indicator.Indicator;
import com.stox.module.indicator.Stochastics;
import com.stox.module.indicator.Stochastics.Config;
import com.stox.module.indicator.model.MultiValue;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import lombok.experimental.Delegate;

public class ChartStochastics extends AbstractChartIndicator<Config, MultiValue, List<Point2D>> {

	@Delegate
	private final Stochastics indicator = Indicator.ofType(Stochastics.class);
	
	@Override
	public String name() {
		return "Stochastics";
	}

	@Override
	public UnitParent<List<Point2D>> parent(Group group) {
		return new MultiValueUnitParent(group, 2);
	}

	@Override
	public Unit<MultiValue> unit(Parent<List<Point2D>> parent) {
		return new MultiValueUnit(parent);
	}

	@Override
	public boolean groupable() {
		return false;
	}

	@Override
	public Underlay underlay(Config config) {
		return Underlay.NONE;
	}

	@Override
	public List<ChartAddIn<MultiValue>> addIns(Config config, UnitParent<List<Point2D>> parent) {
		final MultiValueUnitParent polylineUnitParent = (MultiValueUnitParent) parent;
		final ArrayList<ChartAddIn<MultiValue>> addIns = new ArrayList<>(3);
		addIns.add(new LevelAddIn<>(() -> 50d));
		addIns.add(new OverboughtAddIn<>(polylineUnitParent.getPrimaryPolyline(), () -> 70d));
		addIns.add(new OversoldAddIn<>(polylineUnitParent.getPrimaryPolyline(), () -> 30d));
		return addIns;
	}

	@Override
	public double min(MultiValue value) {
		return null == value ? 0 : Arrays.stream(value.getValues()).min().orElseGet(() -> 0);
	}

	@Override
	public double max(MultiValue value) {
		return null == value ? 0 : Arrays.stream(value.getValues()).max().orElseGet(() -> 0);
	}

}
