package com.stox.explorer;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.stox.chart.ScripListView;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripSelectionEvent;
import com.stox.common.scrip.ScripService;
import com.stox.common.scrip.ScripsChangedEvent;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExplorerScripListView extends ListView<Scrip> implements ScripListView, 
	ListenableFutureCallback<List<Scrip>>, ChangeListener<Scrip> {
	
	private final ScripService scripService;
	private final ApplicationEventPublisher eventPublisher;
	
	@PostConstruct
	public void init() {
		scripService.findAllAsync().addCallback(this);
		getSelectionModel().selectedItemProperty().addListener(this);
	}
	
	@Override
	public void changed(ObservableValue<? extends Scrip> observable, Scrip oldValue, Scrip newValue) {
		eventPublisher.publishEvent(new ScripSelectionEvent(newValue));
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
