package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottablePVolatilityIndicator.PVolatilityConfig;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.PVolatilityIndicator;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import lombok.Data;

public class PlottablePVolatilityIndicator implements Plottable<Num, PVolatilityConfig, Point2D> {

	@Data
	public static class PVolatilityConfig {
		private int barCount = 34;
		public String toString() {return String.format("BarCount: %d", barCount);}
	}

	@Override
	public String toString() {
		return "PVolaitlity";
	}
	
	@Override
	public PVolatilityConfig createConfig() {
		return new PVolatilityConfig();
	}

	@Override
	public Indicator<Num> createIndicator(PVolatilityConfig config, BarSeries barSeries) {
		return new PVolatilityIndicator(barSeries, config.getBarCount());
	}

	@Override
	public double resolveLowValue(Num model) {
		return null == model ? 0 : ( model.isNaN() ? 1 :  model.doubleValue());
	}

	@Override
	public double resolveHighValue(Num model) {
		return null == model ? 0 : ( model.isNaN() ? 1 :  model.doubleValue());
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
	public ConfigView createConfigView(PVolatilityConfig config) {
		return new AutoForm(config);
	}
	
}
