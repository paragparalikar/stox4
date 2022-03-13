package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.ChartingView;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableSMAIndicator.SmaIndicatorConfig;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.bar.BarValueType;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import lombok.Data;

public class PlottableSMAIndicator implements Plottable<Num, SmaIndicatorConfig, Point2D>{

	@Data
	public static class SmaIndicatorConfig {
		private int span = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
	}
	
	@Override
	public String toString() {
		return "Simple Moving Average (SMA)";
	}
	
	@Override
	public void add(ChartingView chartingView) {
		chartingView.add(new Plot<>(this));
	}

	@Override
	public double resolveLowValue(Num model) {
		return Double.MAX_VALUE;
	}

	@Override
	public double resolveHighValue(Num model) {
		return Double.MIN_VALUE;
	}

	@Override
	public Unit<Num, Point2D> createUnit() {
		return new LineUnit();
	}

	@Override
	public UnitParent<Point2D> createUnitParent() {
		return new PolylineUnitParent(new Polyline());
	}

	@Override
	public SmaIndicatorConfig createConfig() {
		return new SmaIndicatorConfig();
	}

	@Override
	public ConfigView createConfigView(SmaIndicatorConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(SmaIndicatorConfig config, BarSeries barSeries) {
		return new SMAIndicator(createIndicator(config.getBarValueType(), barSeries), config.getSpan());
	}
	
}
