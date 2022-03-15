package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableVolatilityContractionIndicator.VolatilityContractionConfig;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.VolatilityContractionIndicator;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import lombok.Data;

public class PlottableVolatilityContractionIndicator implements Plottable<Num, VolatilityContractionConfig, Point2D> {

	@Data
	public static class VolatilityContractionConfig {
		private int barCount = 34;
	}

	@Override
	public String toString() {
		return "Volaitlity Contraction";
	}
	
	@Override
	public VolatilityContractionConfig createConfig() {
		return new VolatilityContractionConfig();
	}

	@Override
	public Indicator<Num> createIndicator(VolatilityContractionConfig config, BarSeries barSeries) {
		final Indicator<Num> indicator = new VolatilityContractionIndicator(barSeries, config.getBarCount());
		return new StochasticRSIIndicator(indicator, config.getBarCount());
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
	public ConfigView createConfigView(VolatilityContractionConfig config) {
		return new AutoForm(config);
	}
	
}
