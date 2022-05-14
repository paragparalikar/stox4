package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableStochasticRSIIndicator.StochasticRSIIndicatorConfig;
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

public class PlottableStochasticRSIIndicator implements Plottable<Num, StochasticRSIIndicatorConfig, Point2D> {

	@Data
	public static class StochasticRSIIndicatorConfig{
		private int barCount = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
		public String toString() {return String.format("BarCount: %d, Type: %s", barCount, barValueType.name());}
	}
	
	@Override
	public String toString() {
		return "Stochastic RSI (SRI)";
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
	public StochasticRSIIndicatorConfig createConfig() {
		return new StochasticRSIIndicatorConfig();
	}

	@Override
	public ConfigView createConfigView(StochasticRSIIndicatorConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(StochasticRSIIndicatorConfig config, BarSeries barSeries) {
		final Indicator<Num> indicator = config.getBarValueType().createIndicator(barSeries);
		return new StochasticRSIIndicator(indicator, config.getBarCount());
	}
	
}
