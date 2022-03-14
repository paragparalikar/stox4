package com.stox.charting.plot.rule;

import com.stox.charting.plot.Plottable;
import com.stox.charting.unit.BooleanUnit;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.parent.GroupUnitParent;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;

import javafx.scene.Group;
import javafx.scene.Node;

public abstract class AbstractPlottableRule<T> implements Plottable<Boolean, T, Node>{

	@Override
	public double resolveLowValue(Boolean model) {
		return Double.MAX_VALUE;
	}

	@Override
	public double resolveHighValue(Boolean model) {
		return Double.MIN_VALUE;
	}

	@Override
	public Unit<Boolean, Node> createUnit() {
		return new BooleanUnit();
	}

	@Override
	public UnitParent<Node> createUnitParent() {
		return new GroupUnitParent(new Group());
	}

	@Override
	public ConfigView createConfigView(T config) {
		return new AutoForm(config);
	}

}
