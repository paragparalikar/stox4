package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableATRIndicator.ATRIndicatorConfig;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import lombok.Data;

public class PlottableATRIndicator implements Plottable<Num, ATRIndicatorConfig, Point2D> {

	@Data
	public static class ATRIndicatorConfig {
		private int barCount = 14;
	}
	
	@Override
	public String toString() {
		return "Average True Range (ATR)";
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
	public Unit<Num, Point2D> createUnit() {
		return new LineUnit();
	}

	@Override
	public UnitParent<Point2D> createUnitParent() {
		return new PolylineUnitParent(new Polyline());
	}

	@Override
	public ATRIndicatorConfig createConfig() {
		return new ATRIndicatorConfig();
	}

	@Override
	public ConfigView createConfigView(ATRIndicatorConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(ATRIndicatorConfig config, BarSeries barSeries) {
		return new ATRIndicator(barSeries, config.getBarCount());
	}

}
