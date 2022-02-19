package com.stox.charting.plot;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.helpers.ConstantIndicator;

import com.stox.charting.unit.BooleanUnit;

import javafx.scene.control.Label;

public class RulePlot extends Plot<Boolean> {

	private final PlotBuilder<Boolean> ruleBuilder;
	private final RulePlotInfo plotInfo = new RulePlotInfo();
	
	public RulePlot(PlotBuilder<Boolean> ruleBuilder) {
		super(BooleanUnit::new);
		this.ruleBuilder = ruleBuilder;
	}

	@Override
	public void reload() {
		final BarSeries barSeries = getContext().getBarSeriesProperty().get();
		if(null != barSeries) {
			plotInfo.setName(ruleBuilder.getName());
			setIndicator(ruleBuilder.build(barSeries));
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

class RulePlotInfo extends PlotInfo<Boolean> {
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
	public void setValue(Boolean model) {
		valueLabel.setVisible(null != model);
		valueLabel.setText(null == model ? null : model.toString().toUpperCase());
	}
	
}
