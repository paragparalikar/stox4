package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.ChartingView;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableEMAIndicator.EmaIndicatorConfig;
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


public class PlottableEMAIndicator implements Plottable<Num, EmaIndicatorConfig, Point2D> {

	@Data
	public static class EmaIndicatorConfig {
		private int barCount = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
		public String toString() {return String.format("BarCount: %d, Type: %s", barCount, barValueType.name());}
	}
	
	@Override
	public String toString() {
		return "Exponential Moving Average (EMA)";
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
	public EmaIndicatorConfig createConfig() {
		return new EmaIndicatorConfig();
	}

	@Override
	public ConfigView createConfigView(EmaIndicatorConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(EmaIndicatorConfig config, BarSeries barSeries) {
		final Indicator<Num> indicator = config.getBarValueType().createIndicator(barSeries);
		return new EMAIndicator(indicator, config.getBarCount());
	}
	
}
