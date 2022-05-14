package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableStandardDeviationIndicator.StandardDeviationConfig;
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

public class PlottableStandardDeviationIndicator implements Plottable<Num, StandardDeviationConfig, Point2D>{

	@Data
	public static class StandardDeviationConfig {
		private int barCount = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
		public String toString() {return String.format("BarCount: %d, Type: %s", barCount, barValueType.name());}
	}
	
	@Override
	public String toString() {
		return "Standard Deviation (SDI)";
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
	public StandardDeviationConfig createConfig() {
		return new StandardDeviationConfig();
	}

	@Override
	public ConfigView createConfigView(StandardDeviationConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(StandardDeviationConfig config, BarSeries barSeries) {
		final Indicator<Num> indicator = config.getBarValueType().createIndicator(barSeries);
		return new StandardDeviationIndicator(indicator, config.getBarCount());
	}
	
}
