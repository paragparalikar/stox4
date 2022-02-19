package com.stox.charting.tools;

import org.ta4j.core.Rule;

import com.stox.charting.ChartingView;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.plot.RulePlot;
import com.stox.rule.TestRule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

public class RulesButton extends Button implements EventHandler<ActionEvent> {

	private final ChartingContext context;
	private final ChartingView chartingView;
	
	private ListView<Rule> listView;
	
	public RulesButton(ChartingView chartingView, ChartingContext context) {
		super("Rules");
		setOnAction(this);
		this.context = context;
		this.chartingView = chartingView;
	}
	
	private ListView<Rule> getRuleListView(){
		if(null == listView) {
			final ObservableList<Rule> rules = FXCollections.observableArrayList(new TestRule());
			listView = new ListView<>(rules);
		}
		return listView;
	}

	@Override
	public void handle(ActionEvent event) {
		
	}
	
	private void onAction(ButtonType buttonType) {
		if(ButtonType.OK.equals(buttonType)) {
			final Rule rule = getRuleListView().getSelectionModel().getSelectedItem();
			if(null != rule) {
				final RulePlot rulePlot = new RulePlot(rule);
				chartingView.add(rulePlot);
			}
		}
	}
	
}
