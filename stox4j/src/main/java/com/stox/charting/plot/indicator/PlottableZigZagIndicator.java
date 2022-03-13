package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.charting.ChartingView;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableZigZagIndicator.ZigZagConfig;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.ZigZagUnit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.Move;
import com.stox.indicator.ZigZagIndicator;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import lombok.Data;

public class PlottableZigZagIndicator implements Plottable<Move, ZigZagConfig, Point2D>{

	@Data
	public static class ZigZagConfig {
		private int barCount = 144;
		private double tolarancePercentage = 10;
	}
	
	@Override
	public String toString() {
		return "ZigZag";
	}
	
	@Override
	public void add(ChartingView chartingView) {
		chartingView.add(new ZigZagPlot(this));
	}

	@Override
	public ZigZagConfig createConfig() {
		return new ZigZagConfig();
	}

	@Override
	public Indicator<Move> createIndicator(ZigZagConfig config, BarSeries barSeries) {
		return new ZigZagIndicator(barSeries, config.getBarCount(), config.getTolarancePercentage());
	}

	@Override
	public double resolveLowValue(Move model) {
		return Double.MAX_VALUE;
	}

	@Override
	public double resolveHighValue(Move model) {
		return Double.MIN_VALUE;
	}

	@Override
	public Unit<Move, Point2D> createUnit() {
		return new ZigZagUnit();
	}

	@Override
	public UnitParent<Point2D> createUnitParent() {
		return new PolylineUnitParent(new Polyline());
	}

	@Override
	public ConfigView createConfigView(ZigZagConfig config) {
		return new AutoForm(config);
	}
	
}
