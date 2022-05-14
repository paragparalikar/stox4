package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottablePMomentumIndicator.PMomentumConfig;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.PMomentumIndicator;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import lombok.Data;

public class PlottablePMomentumIndicator implements Plottable<Num, PMomentumConfig, Point2D>  {

	@Data
	public static class PMomentumConfig {
		private int barCount = 34;
		public String toString() {return String.format("BarCount: %d", barCount);}
	}

	@Override
	public String toString() {
		return "PMomentum";
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
	public PMomentumConfig createConfig() {
		return new PMomentumConfig();
	}

	@Override
	public ConfigView createConfigView(PMomentumConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(PMomentumConfig config, BarSeries barSeries) {
		return new PMomentumIndicator(config.getBarCount(), barSeries);
	}
	
}
