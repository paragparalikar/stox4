package com.stox.charting.tools;

import org.ta4j.core.BarSeries;

import com.stox.charting.ChartingView;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.chart.Chart;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableADXIndicator;
import com.stox.charting.plot.indicator.PlottableATRIndicator;
import com.stox.charting.plot.indicator.PlottableRSIIndicator;
import com.stox.charting.plot.indicator.PlottableStochasticRSIIndicator;
import com.stox.common.scrip.Scrip;
import com.stox.common.ui.DefaultDialogx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

public class IndicatorButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingContext context;
	private final ChartingView chartingView;
	private final ListView<Plottable<?, ?, ?>> listView = new ListView<>();
	
	public IndicatorButton(ChartingView chartingView, ChartingContext context) {
		super("Indicators");
		setOnAction(this);
		this.context = context;
		this.chartingView = chartingView;
		listView.getItems().add(new PlottableRSIIndicator());
		listView.getItems().add(new PlottableADXIndicator());
		listView.getItems().add(new PlottableATRIndicator());
		listView.getItems().add(new PlottableStochasticRSIIndicator());
	}

	@Override
	public void handle(ActionEvent event) {
		final Scrip scrip = context.getScripProperty().get();
		final BarSeries barSeries = context.getBarSeriesProperty().get();
		if(null != scrip && null != barSeries && 0 < barSeries.getBarCount()) {
			showDialog();
		}
	}

	private void showDialog() {
		new DefaultDialogx()
		.withTitle("Indicators")
		.withContent(listView)
		.withButton(ButtonType.CANCEL)
		.withButton(ButtonType.APPLY, this::action)
		.show(this);
	}
	
	private void action() {
		final Plottable<?, ?, ?> plottable = listView.getSelectionModel().getSelectedItem();
		if(null != plottable) {
			final Chart chart = new Chart(chartingView);
			chartingView.add(chart);
			chart.add(new Plot(plottable));
			Platform.runLater(chartingView::redraw);
		}
	}
}
