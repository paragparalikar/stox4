package com.stox.module.charting.indicator;

import java.util.Collections;
import java.util.List;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.indicator.addin.ChartAddIn;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.unit.BarUnit;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.GroupUnitParent;
import com.stox.module.charting.unit.parent.UnitParent;
import com.stox.module.indicator.DecisionIndicator;
import com.stox.module.indicator.DecisionIndicator.Config;
import com.stox.module.indicator.Indicator;

import javafx.scene.Group;
import javafx.scene.Node;
import lombok.experimental.Delegate;

public class ChartDecision extends AbstractChartIndicator<Config, Double, Node> {

	@Delegate
	private final Indicator<DecisionIndicator.Config, Double> indicator = Indicator.ofType(DecisionIndicator.class);

	@Override
	public UnitParent<Node> parent(Group group) {
		return new GroupUnitParent(group);
	}

	@Override
	public boolean groupable() {
		return false;
	}

	@Override
	public Underlay underlay(Config config) {
		return Underlay.NONE;
	}

	@Override
	public double min(Double value) {
		return null == value ? Double.MAX_VALUE : value;
	}

	@Override
	public double max(Double value) {
		return null == value ? Double.MIN_VALUE : value;
	}

	@Override
	public String name() {
		return "Decision";
	}

	@Override
	public Unit<Double> unit(Parent<Node> parent) {
		return new BarUnit(parent);
	}

	@Override
	public List<ChartAddIn<Double>> addIns(Config config, UnitParent<Node> parent) {
		return Collections.emptyList();
	}

}
