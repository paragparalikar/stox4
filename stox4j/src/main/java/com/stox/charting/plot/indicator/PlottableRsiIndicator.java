package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableRsiIndicator.RsiIndicatorConfig;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.bar.BarValueType;
import com.stox.common.ui.ConfigView;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polyline;
import lombok.Data;

public class PlottableRsiIndicator implements ConfigView<RsiIndicatorConfig>, Plottable<Num, RsiIndicatorConfig, Point2D> {

	@Data
	public static class RsiIndicatorConfig {
		private int span = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
	}
	
	private final GridPane container = new GridPane();
	
	@Override
	public String toString() {
		return "Relative Strength Index";
	}
	
	@Override
	public void updateView(RsiIndicatorConfig config) {
		
	}
	
	@Override
	public void updateConfig(RsiIndicatorConfig config) {
		
	}
	
	@Override
	public Node getNode() {
		return container;
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
	public RsiIndicatorConfig createConfig() {
		return new RsiIndicatorConfig();
	}

	@Override
	public ConfigView<RsiIndicatorConfig> createConfigView() {
		return this;
	}

	@Override
	public Indicator<Num> createIndicator(RsiIndicatorConfig config, BarSeries barSeries) {
		return new RSIIndicator(createIndicator(config.getBarValueType(), barSeries), config.getSpan());
	}
	
}
