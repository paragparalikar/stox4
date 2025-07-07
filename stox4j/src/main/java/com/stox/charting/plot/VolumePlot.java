package com.stox.charting.plot;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import org.ta4j.core.num.Num;

import com.stox.charting.plot.indicator.PlottableVolumeIndicator;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class VolumePlot extends YAxisPlot<Num, Void, Node> {

	public VolumePlot(PlottableVolumeIndicator indicator) {
		super(indicator);
		setInfo(new VolumePlotInfo(this));
	}

	@Override
	public Plot<Num, Void, Node> setColor(Color color) {
		return this;
	}

}

class VolumePlotInfo extends DefaultPlotInfo<Num> {
	
	private final NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.getDefault());

	public VolumePlotInfo(Plot<Num, ?, ?> plot) {
		super(plot);
		numberFormat.setMaximumFractionDigits(0);
		numberFormat.setMinimumIntegerDigits(1);
		numberFormat.setGroupingUsed(true);
		numberFormat.setRoundingMode(RoundingMode.CEILING);
	}
	
	@Override
	public void setValue(Num model) {
		setValueText(null == model ? null : numberFormat.format(model.doubleValue()));
	}
}
