package com.stox.explorer;

import com.stox.charting.ChartingView;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class ExplorerView extends BorderPane {

	private final ChartingView chartingView;
	private final ListView<Scrip> listView = new ListView<>();	
	
	public ExplorerView(ScripService scripService, ChartingView chartingView) {
		this.chartingView = chartingView;
		setCenter(listView);
		listView.getItems().addAll(scripService.findAll());
		listView.getSelectionModel().selectedItemProperty().addListener(this::onScripSelected);
	}
	
	private void onScripSelected(ObservableValue<? extends Scrip> observable, Scrip oldValue, Scrip newValue) {
		chartingView.setScrip(newValue);
	}
}
