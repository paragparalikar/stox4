package com.stox.charting.tools;

import org.ta4j.core.BarSeries;

import com.stox.charting.ChartingView;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.indicator.PlottableRsiIndicator;
import com.stox.common.scrip.Scrip;
import com.stox.common.ui.DefaultDialogx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

public class IndicatorButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingContext context;
	private final ChartingView chartingView;
	
	public IndicatorButton(ChartingView chartingView, ChartingContext context) {
		super("Indicators");
		setOnAction(this);
		this.context = context;
		this.chartingView = chartingView;
	}

	@Override
	public void handle(ActionEvent event) {
		final Scrip scrip = context.getScripProperty().get();
		final BarSeries barSeries = context.getBarSeriesProperty().get();
		if(null != scrip && null != barSeries && 0 < barSeries.getBarCount()) {
			final ListView<Plottable<?, ?, ?>> listView = new ListView<>();
			listView.getItems().add(new PlottableRsiIndicator());
			new DefaultDialogx()
				.withTitle("Indicators")
				.withContent(listView)
				.withButton(ButtonType.CANCEL)
				.withButton(ButtonType.APPLY, () -> {
					final Plottable<?, ?, ?> plottable = listView.getSelectionModel().getSelectedItem();
					if(null != plottable) chartingView.createChart().add(new Plot(plottable));
					
				})
				.show(this);
		}
	}


}
