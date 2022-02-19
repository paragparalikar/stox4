package com.stox.charting.plot;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.helpers.ConstantIndicator;

import com.stox.charting.chart.PlotInfo;
import com.stox.charting.tools.RuleBuilder;
import com.stox.charting.unit.BooleanUnit;
import com.stox.indicator.RuleIndicator;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class RulePlot extends Plot<Boolean> {

	private final RuleBuilder ruleBuilder;
	private final RulePlotInfo plotInfo = new RulePlotInfo();
	
	public RulePlot(RuleBuilder ruleBuilder) {
		super(BooleanUnit::new);
		this.ruleBuilder = ruleBuilder;
	}

	@Override
	public void reload() {
		final BarSeries barSeries = getContext().getBarSeriesProperty().get();
		if(null != barSeries) {
			plotInfo.setName(ruleBuilder.getName());
			final Rule rule = ruleBuilder.build(barSeries);
			setIndicator(new RuleIndicator(rule, barSeries));
		} else {
			plotInfo.setName(null);
			setIndicator(new ConstantIndicator<>(null, false));
		}
	}
	
	@Override public void updateYAxis(int startIndex, int endIndex) {}
	@Override protected double resolveLowValue(Boolean model) {return 0;}
	@Override protected double resolveHighValue(Boolean model) {return 0;}

	@Override
	public PlotInfo<Boolean> getInfo() {
		return plotInfo;
	}
}

class RulePlotInfo extends HBox implements PlotInfo<Boolean> {
	private final Label nameLabel = new Label();
	private final Label valueLabel = new Label();

	public RulePlotInfo() {
		nameLabel.getStyleClass().add("plot-name");
		getStyleClass().add("plot-info-pane");
		getChildren().addAll(nameLabel, valueLabel);
	}
	
	public void setName(String value) {
		setVisible(null != value);
		nameLabel.setText(null == value ? null : value.toUpperCase());
	}
	
	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void show(Boolean model) {
		valueLabel.setVisible(null != model);
		valueLabel.setText(null == model ? null : model.toString().toUpperCase());
	}
	
}
