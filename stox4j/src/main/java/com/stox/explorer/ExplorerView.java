package com.stox.explorer;

import com.stox.charting.ChartingView.ChartingContext;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class ExplorerView extends BorderPane {

	private final ChartingContext context;
	private final ListView<Scrip> listView = new ListView<>();	
	
	public ExplorerView(ScripService scripService, ChartingContext context) {
		this.context = context;
		
		setCenter(listView);
		listView.getItems().addAll(scripService.findAll());
		listView.getSelectionModel().selectedItemProperty().addListener(this::onScripSelected);
		if(!listView.getItems().isEmpty()) {
			listView.getSelectionModel().select(0);
		}
	}
	
	private void onScripSelected(ObservableValue<? extends Scrip> observable, Scrip oldValue, Scrip newValue) {
		context.getScripProperty().set(newValue);
	}
	
	
	
}
