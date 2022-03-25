package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableADXIndicator.ADXIndicatorConfig;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import lombok.Data;

public class PlottableADXIndicator implements Plottable<Num, ADXIndicatorConfig, Point2D> {

	@Data
	public static class ADXIndicatorConfig{
		private int barCount = 14;
		public String toString() {return String.format("BarCount: %d", barCount);}
	}
	
	@Override
	public String toString() {
		return "Average Directional Index (ADX)";
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
	public ADXIndicatorConfig createConfig() {
		return new ADXIndicatorConfig();
	}

	@Override
	public ConfigView createConfigView(ADXIndicatorConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(ADXIndicatorConfig config, BarSeries barSeries) {
		return new ADXIndicator(barSeries, config.getBarCount());
	}

}
