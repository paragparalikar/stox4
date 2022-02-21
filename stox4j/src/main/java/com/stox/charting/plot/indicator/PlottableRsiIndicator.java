package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableRsiIndicator.RsiIndicatorConfig;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PathUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.bar.BarValueType;
import com.stox.common.ui.ConfigView;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import lombok.Data;

public class PlottableRsiIndicator extends GridPane implements ConfigView<RsiIndicatorConfig>, Plottable<Num, RsiIndicatorConfig, PathElement> {

	@Data
	public static class RsiIndicatorConfig {
		private int span = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
	}
	
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
		return this;
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
	public Unit<Num, PathElement> createUnit() {
		return new LineUnit();
	}

	@Override
	public UnitParent<PathElement> createUnitParent() {
		return new PathUnitParent(new Path());
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
