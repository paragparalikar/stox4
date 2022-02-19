package com.stox.charting.tools;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.charting.ChartingView;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.plot.RulePlot;
import com.stox.common.scrip.Scrip;
import com.stox.common.ui.DefaultDialogx;
import com.stox.rule.TestRule;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

public class RulesButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingContext context;
	private final ChartingView chartingView;
	
	public RulesButton(ChartingView chartingView, ChartingContext context) {
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
			final ListView<RuleBuilder> listView = new ListView<>();
			listView.getItems().add(new RuleBuilder("Test 1", null, TestRule::new));
			listView.getItems().add(new RuleBuilder("Test 2", null, TestRule::new));
			listView.getItems().add(new RuleBuilder("Test 3", null, TestRule::new));
			
			new DefaultDialogx()
				.withTitle("Rules")
				.withContent(listView)
				.withButton(ButtonType.CANCEL)
				.withButton(ButtonType.APPLY, () -> {
					final RuleBuilder ruleBuilder = listView.getSelectionModel().getSelectedItem();
					if(null != ruleBuilder) {
						final RulePlot rulePlot = new RulePlot(ruleBuilder);
						chartingView.add(rulePlot);
					}
				})
				.show(this);
		}
	}	
}