package com.stox.explorer;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.stox.chart.ScripListView;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.scrip.ScripsChangedEvent;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExplorerScripListView extends ListView<Scrip> implements ScripListView, ListenableFutureCallback<List<Scrip>> {
	
	public ExplorerScripListView(final ScripService scripService) {
		scripService.findAllAsync().addCallback(this);
	}
	
	@Override
	public String getDisplayName() {
		return "Instrument Explorer";
	}

	@Override
	public Node getNode() {
		return this;
	}
	
	@Override
	public void onFailure(Throwable ex) {
		log.error("Failed to fetch scrips", ex);
	}

	@Override
	public void onSuccess(List<Scrip> result) {
		Platform.runLater(() -> getItems().setAll(result));
	}
	
	@EventListener
	public void onScripsChanged(ScripsChangedEvent event) {
		onSuccess(event.getScrips());
	}

}
