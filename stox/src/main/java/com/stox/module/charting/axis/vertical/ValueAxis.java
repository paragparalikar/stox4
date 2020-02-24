package com.stox.module.charting.axis.vertical;

import com.stox.fx.widget.StaticLayoutPane;
import com.stox.util.StringUtil;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ValueAxis extends StaticLayoutPane {
	private static final double[] TICK_UNIT_DEFAULTS = { 1.0E-10d, 2.5E-10d, 5.0E-10d, 1.0E-9d, 2.5E-9d, 5.0E-9d,
			1.0E-8d, 2.5E-8d, 5.0E-8d, 1.0E-7d, 2.5E-7d, 5.0E-7d, 1.0E-6d, 2.5E-6d, 5.0E-6d, 1.0E-5d, 2.5E-5d, 5.0E-5d,
			1.0E-4d, 2.5E-4d, 5.0E-4d, 0.0010d, 0.0025d, 0.0050d, 0.01d, 0.025d, 0.05d, 0.1d, 0.25d, 0.5d, 1.0d, 2.5d,
			5.0d, 10.0d, 25.0d, 50.0d, 100.0d, 250.0d, 500.0d, 1000.0d, 2500.0d, 5000.0d, 10000.0d, 25000.0d, 50000.0d,
			100000.0d, 250000.0d, 500000.0d, 1000000.0d, 2500000.0d, 5000000.0d, 1.0E7d, 2.5E7d, 5.0E7d, 1.0E8d, 2.5E8d,
			5.0E8d, 1.0E9d, 2.5E9d, 5.0E9d, 1.0E10d, 2.5E10d, 5.0E10d, 1.0E11d, 2.5E11d, 5.0E11d, 1.0E12d, 2.5E12d,
			5.0E12d };

	private int count;

	public ValueAxis(final int count) {
		this.count = count;
		getStyleClass().add("value-axis");
		setPadding(3, 1, 2, 1);
		setLabelHeight(new Text("1").getLayoutBounds().getHeight());
	}

	public void layoutChartChildren(final YAxis yAxis) {
		getChildren().clear();
		final double min = yAxis.getMin();
		final double max = yAxis.getMax();
		final double range = snap(Math.abs(max - min) / count);
		if (0 < range) {
			for (double value = min + (range - min % range); value <= max; value += range) {
				tick(value, yAxis);
			}
		}
	}


	protected void tick(Double value, YAxis yAxis) {
		add(0, yAxis.getY(value) - getLabelHeight() / 2 - getPaddingTop(),
				new Label(StringUtil.stringValueOf(value)));
	}

	protected double snap(double value) {
		for (final double tickUnitDefault : TICK_UNIT_DEFAULTS) {
			if (value <= tickUnitDefault) {
				return tickUnitDefault;
			}
		}
		return value;
	}

}
