package com.stox.charting.plot.indicator;

import org.ta4j.core.num.Num;

import com.stox.charting.plot.Plottable;
import com.stox.charting.unit.LineUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.PolylineUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;

public interface PlottableLineIndicator<T> extends Plottable<Num, T, Point2D>{
	
	@Override
	public default ConfigView createConfigView(T config) {
		return new AutoForm(config);
	}
	
	@Override
	public default Unit<Num, Point2D> createUnit() {
		return new LineUnit();
	}
	
	@Override
	public default UnitParent<Point2D> createUnitParent() {
		return new PolylineUnitParent(new Polyline());
	}
	
	@Override
	public default double resolveHighValue(Num model) {
		return model.doubleValue();
	}
	
	@Override
	public default double resolveLowValue(Num model) {
		return model.doubleValue();
	}
	
}
