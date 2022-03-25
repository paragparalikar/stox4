package com.stox.charting.plot;

import org.ta4j.core.num.Num;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.Icon;
import com.stox.common.ui.modal.Modal;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class DefaultPlotInfo<T> extends PlotInfo<T> {
	
	private final Label valueLabel = new Label();

	public DefaultPlotInfo(Plot<T, ?, ?> plot) {
		super(plot);
		getChildren().add(2, valueLabel);
		if(null != plot.getIndicatorConfig()) createButton(Icon.GEAR, event -> config());
		createButton(Icon.TIMES, event -> plot.getChart().removePlot(plot));
	}
	
	private void config() {
		final ConfigView configView = getPlot().createConfigView();
		final Label graphics = new Label(Icon.CHECK);
		graphics.getStyleClass().add("icon");
		final Button button = new Button("Apply", graphics);
		final Modal modal = new Modal()
			.withTitleIcon(Icon.GEAR)
			.withTitleText("Configure")
			.withContent(configView.getNode())
			.withButton(button)
			.show(this);
		button.setOnAction(event -> {
			configView.populateModel();
			modal.hide();
			getPlot().reload();
		});
	}
	
	@Override
	public void setValue(T model) {
		valueLabel.setVisible(null != model);
		setValueText(null == model ? null : toString(model));
	}
	
	public void setValueText(String text) {
		valueLabel.setText(text);
	}
	
	private String toString(T model) {
		double value = 0;
		if(model instanceof Num) {
			value = ((Num) model).doubleValue();
		} else if (model instanceof Number) {
			value = ((Number) model).doubleValue();
		} else {
			return model.toString();
		}
		return String.format("%.2f", value);
	}
	
	@Override
	public void setColor(Color color) {
		super.setColor(color);
		valueLabel.setTextFill(color);
	}

}
