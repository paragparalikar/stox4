package com.stox.charting.tools;

import org.ta4j.core.BarSeries;

import com.stox.charting.ChartingView;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.rule.PlottableBreakoutBarRule;
import com.stox.charting.plot.rule.PlottableReaccumulationRule;
import com.stox.charting.plot.rule.RulePlot;
import com.stox.common.scrip.Scrip;
import com.stox.common.ui.DefaultDialogx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

public class RuleButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingContext context;
	private final ChartingView chartingView;
	
	public RuleButton(ChartingView chartingView, ChartingContext context) {
		super("Rules");
		setOnAction(this);
		this.context = context;
		this.chartingView = chartingView;
	}

	@Override
	public void handle(ActionEvent event) {
		final Scrip scrip = context.getScripProperty().get();
		final BarSeries barSeries = context.getBarSeriesProperty().get();
		if(null != scrip && null != barSeries && 0 < barSeries.getBarCount()) {
			final ListView<Plottable<Boolean, ?, Node>> listView = new ListView<>();
			listView.getItems().add(new PlottableBreakoutBarRule());
			listView.getItems().add(new PlottableReaccumulationRule());
			new DefaultDialogx()
				.withTitle("Rules")
				.withContent(listView)
				.withButton(ButtonType.CANCEL)
				.withButton(ButtonType.APPLY, () -> {
					final Plottable<Boolean, ?, Node> plottable = listView.getSelectionModel().getSelectedItem();
					if(null != plottable) {
						final RulePlot<?> rulePlot = new RulePlot(plottable);
						chartingView.add(rulePlot);
					}
				})
				.show(this);
		}
	}	
}