package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.charting.ChartingView;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableUpperVolatilityBandIndicator.UpperVolatilityBandConfig;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.UpperVolatilityBandIndicator;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import lombok.Data;

public class PlottableUpperVolatilityBandIndicator implements Plottable<Num, UpperVolatilityBandConfig, Point2D>{

	@Data
	public static class UpperVolatilityBandConfig {
		private int barCount = 5;
		public String toString() {return String.format("BarCount: %d", barCount);}
	}

	@Override
	public String toString() {
		return "Volatility Band - Upper";
	}
	
	@Override
	public void add(ChartingView chartingView) {
		chartingView.add(new Plot<>(this));
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
	public UpperVolatilityBandConfig createConfig() {
		return new UpperVolatilityBandConfig();
	}

	@Override
	public ConfigView createConfigView(UpperVolatilityBandConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(UpperVolatilityBandConfig config, BarSeries barSeries) {
		return new UpperVolatilityBandIndicator(barSeries, config.getBarCount());
	}
	
}
