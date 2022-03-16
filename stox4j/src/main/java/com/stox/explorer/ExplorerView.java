package com.stox.explorer;

import java.util.Comparator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.common.event.ScripMasterDownloadedEvent;
import com.stox.common.event.ScripSelectedEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Fx;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class ExplorerView extends BorderPane {

	private final EventBus eventBus;
	private final ListView<Scrip> listView = new ListView<>();	
	
	public ExplorerView(EventBus eventBus, ScripService scripService) {
		this.eventBus = eventBus;
		setCenter(listView);
		eventBus.register(this);
		listView.getItems().addAll(scripService.findAll());
		listView.getSelectionModel().selectedItemProperty().addListener(this::onScripSelected);
	}
	
	private void onScripSelected(ObservableValue<? extends Scrip> observable, Scrip oldValue, Scrip newValue) {
		eventBus.post(new ScripSelectedEvent(newValue));
	}
	
	@Subscribe
	public void onScripMasterDownloaded(ScripMasterDownloadedEvent event) {
		event.getScrips().sort(Comparator.comparing(Scrip::getName));
		Fx.run(() -> listView.getItems().setAll(event.getScrips()));
	}
}
