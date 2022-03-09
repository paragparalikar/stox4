package com.stox.explorer;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.event.ScripSelectionEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class ExplorerView extends BorderPane {

	private final EventBus eventBus;
	private final ListView<Scrip> listView = new ListView<>();	
	
	public ExplorerView(EventBus eventBus, ScripService scripService) {
		this.eventBus = eventBus;
		setCenter(listView);
		listView.getItems().addAll(scripService.findAll());
		listView.getSelectionModel().selectedItemProperty().addListener(this::onScripSelected);
	}
	
	private void onScripSelected(ObservableValue<? extends Scrip> observable, Scrip oldValue, Scrip newValue) {
		eventBus.post(new ScripSelectionEvent(newValue));
	}
}
