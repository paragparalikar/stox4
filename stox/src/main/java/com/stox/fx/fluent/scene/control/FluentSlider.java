package com.stox.fx.fluent.scene.control;

import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

public class FluentSlider extends Slider implements IFluentControl<FluentSlider> {

	public FluentSlider() {
	}

	public FluentSlider(double min, double max, double value) {
		super(min, max, value);
	}

	@Override
	public FluentSlider getThis() {
		return this;
	}

	public FluentSlider max(double value) {
		setMax(value);
		return this;
	}

	public FluentSlider min(double value) {
		setMin(value);
		return this;
	}

	public FluentSlider value(double value) {
		setValue(value);
		return this;
	}

	public FluentSlider valueChanging(boolean value) {
		setValueChanging(value);
		return this;
	}

	public FluentSlider orientation(Orientation value) {
		setOrientation(value);
		return this;
	}

	public FluentSlider showTickLabels(boolean value) {
		setShowTickLabels(value);
		return this;
	}

	public FluentSlider showTickMarks(boolean value) {
		setShowTickMarks(value);
		return this;
	}

	public FluentSlider majorTickUnit(double value) {
		setMajorTickUnit(value);
		return this;
	}

	public FluentSlider minorTickCount(int value) {
		setMinorTickCount(value);
		return this;
	}

	public FluentSlider snapToTicks(boolean value) {
		setSnapToTicks(value);
		return this;
	}

	public FluentSlider labelFormatter(StringConverter<Double> value) {
		setLabelFormatter(value);
		return this;
	}

	public FluentSlider blockIncreament(double value) {
		setBlockIncrement(value);
		return this;
	}

}
