package com.stox.charting.tools;

import java.nio.file.Path;

import org.ta4j.core.BarSeries;

import com.stox.charting.ChartingContext;
import com.stox.charting.ChartingView;
import com.stox.charting.plot.Plottable;
import com.stox.charting.plot.rule.PlottableBreakoutBarRule;
import com.stox.charting.plot.rule.PlottableDojiRule;
import com.stox.charting.plot.rule.PlottableLowPivoteRule;
import com.stox.charting.plot.rule.PlottableNarrowRangeBarRule;
import com.stox.charting.plot.rule.PlottableReaccumulationRule;
import com.stox.charting.plot.rule.PlottableSpringRule;
import com.stox.charting.plot.rule.PlottableTestRule;
import com.stox.charting.plot.rule.PlottableVolatilityContractionBreakoutRule;
import com.stox.charting.plot.rule.RulePlot;
import com.stox.common.scrip.Scrip;
import com.stox.common.ui.Icon;
import com.stox.common.ui.modal.Modal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class RuleButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingContext context;
	private final ChartingView chartingView;
	private final ListView<Plottable<Boolean, ?, Node>> listView = new ListView<>();
	
	public RuleButton(ChartingView chartingView, ChartingContext context, Path home) {
		super(Icon.FILTER);
		getStyleClass().add("icon");
		setOnAction(this);
		this.context = context;
		this.chartingView = chartingView;
		listView.getItems().add(new PlottableDojiRule());
		listView.getItems().add(new PlottableTestRule());
		listView.getItems().add(new PlottableLowPivoteRule());
		listView.getItems().add(new PlottableSpringRule());
		listView.getItems().add(new PlottableBreakoutBarRule());
		listView.getItems().add(new PlottableReaccumulationRule());
		listView.getItems().add(new PlottableNarrowRangeBarRule());
		listView.getItems().add(new PlottableVolatilityContractionBreakoutRule());
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
			.withTitleIcon(Icon.FILTER)
			.withTitleText("Rules")
			.withContent(listView)
			.withButton(button)
			.show(this);
		button.setOnAction(event -> action(modal));
	}
	
	private void action(Modal modal) {
		final Plottable<Boolean, ?, Node> plottable = listView.getSelectionModel().getSelectedItem();
		if(null != plottable) {
			final RulePlot<?> rulePlot = new RulePlot(plottable);
			chartingView.add(rulePlot);
		}
		modal.hide();
	}
}