package com.stox.charting.tools;

import org.ta4j.core.BarSeries;

import com.stox.charting.ChartingContext;
import com.stox.charting.ChartingView;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableADXIndicator;
import com.stox.charting.plot.indicator.PlottableATRIndicator;
import com.stox.charting.plot.indicator.PlottableChopIndicator;
import com.stox.charting.plot.indicator.PlottableMeanDeviationIndicator;
import com.stox.charting.plot.indicator.PlottablePMomentumIndicator;
import com.stox.charting.plot.indicator.PlottablePVolatilityIndicator;
import com.stox.charting.plot.indicator.PlottablePriceRejectionIndicator;
import com.stox.charting.plot.indicator.PlottableROCIndicator;
import com.stox.charting.plot.indicator.PlottableRSIIndicator;
import com.stox.charting.plot.indicator.PlottableSMAIndicator;
import com.stox.charting.plot.indicator.PlottableSigmaIndicator;
import com.stox.charting.plot.indicator.PlottableStandardDeviationIndicator;
import com.stox.charting.plot.indicator.PlottableStochasticKIndicator;
import com.stox.charting.plot.indicator.PlottableStochasticRSIIndicator;
import com.stox.charting.plot.indicator.PlottableTRIndicator;
import com.stox.charting.plot.indicator.PlottableVarianceIndicator;
import com.stox.charting.plot.indicator.PlottableVolumeIndicator;
import com.stox.charting.plot.indicator.PlottableZigZagIndicator;
import com.stox.common.scrip.Scrip;
import com.stox.common.ui.Icon;
import com.stox.common.ui.modal.Modal;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class IndicatorButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingContext context;
	private final ChartingView chartingView;
	private final ListView<Plottable<?, ?, ?>> listView = new ListView<>();
	
	public IndicatorButton(ChartingView chartingView, ChartingContext context) {
		super(Icon.LINE_CHART);
		getStyleClass().add("icon");
		setOnAction(this);
		this.context = context;
		this.chartingView = chartingView;
		listView.getItems().add(new PlottableTRIndicator());
		listView.getItems().add(new PlottableRSIIndicator());
		listView.getItems().add(new PlottableADXIndicator());
		listView.getItems().add(new PlottableATRIndicator());
		listView.getItems().add(new PlottableSMAIndicator());
		listView.getItems().add(new PlottableROCIndicator());
		listView.getItems().add(new PlottableChopIndicator());
		listView.getItems().add(new PlottableSigmaIndicator());
		listView.getItems().add(new PlottableVolumeIndicator());
		listView.getItems().add(new PlottableZigZagIndicator());
		listView.getItems().add(new PlottableVarianceIndicator());
		listView.getItems().add(new PlottableStochasticKIndicator());
		listView.getItems().add(new PlottableStochasticRSIIndicator());
		listView.getItems().add(new PlottableMeanDeviationIndicator());
		listView.getItems().add(new PlottablePriceRejectionIndicator());
		listView.getItems().add(new PlottableStandardDeviationIndicator());
		listView.getItems().add(new PlottablePVolatilityIndicator());
		listView.getItems().add(new PlottablePMomentumIndicator());
	}

	@Override
	public void handle(ActionEvent event) {
		final Scrip scrip = context.getScrip();
		final BarSeries barSeries = context.getBarSeriesProperty().get();
		if(null != scrip && null != barSeries && 0 < barSeries.getBarCount()) {
			showDialog();
		}
	}

	private void showDialog() {
		final Label graphics = new Label(Icon.PLUS);
		graphics.getStyleClass().add("icon");
		final Button button = new Button("Add", graphics);
		button.setDefaultButton(true);
		final Modal modal = new Modal()
			.withTitleIcon(Icon.LINE_CHART)
			.withTitleText("Add Indicators")
			.withContent(listView)
			.withButton(button)
			.show(this);
		button.setOnAction(event -> action(modal));
	}
	
	private void action(Modal modal) {
		final Plottable<?, ?, ?> plottable = listView.getSelectionModel().getSelectedItem();
		if(null != plottable) {
			plottable.add(chartingView);
			Platform.runLater(chartingView::redraw);
		}
		modal.hide();
	}
}
