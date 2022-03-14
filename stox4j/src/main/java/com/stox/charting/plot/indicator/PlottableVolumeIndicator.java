package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.ChartingView;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.YAxisPlot;
import com.stox.charting.unit.BarUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.GroupUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;

import javafx.scene.Group;
import javafx.scene.Node;

public class PlottableVolumeIndicator implements Plottable<Num, Void, Node> {
	
	@Override
	public String toString() {
		return "Volume";
	}

	@Override
	public double resolveLowValue(Num model) {
		return model.doubleValue();
	}

	@Override
	public double resolveHighValue(Num model) {
		return model.doubleValue();
	}

	@Override
	public Unit<Num, Node> createUnit() {
		final BarUnit unit = new BarUnit();
		unit.getBody().getStyleClass().add("volume-bar");
		return unit;
	}
	
	@Override
	public void add(ChartingView chartingView) {
		chartingView.add(new YAxisPlot<>(this));
	}

	@Override
	public UnitParent<Node> createUnitParent() {
		return new GroupUnitParent(new Group());
	}

	@Override
	public Void createConfig() {
		return null;
	}

	@Override
	public ConfigView createConfigView(Void config) {
		return null;
	}

	@Override
	public Indicator<Num> createIndicator(Void config, BarSeries barSeries) {
		return new VolumeIndicator(barSeries);
	}

}
