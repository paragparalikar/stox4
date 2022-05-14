package com.stox.explorer;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.common.event.ScripMasterDownloadedEvent;
import com.stox.common.event.ScripSelectedEvent;
import com.stox.common.persistence.SerializationService;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Fx;
import com.stox.common.ui.View;
import com.sun.javafx.scene.control.skin.ListViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class ExplorerView extends BorderPane implements View {

	private final EventBus eventBus;
	private final ScripService scripService;
	private final SerializationService serializationService;
	private final ListView<Scrip> listView = new ListView<>();	
	
	public ExplorerView(EventBus eventBus, ScripService scripService, SerializationService serializationService) {
		this.eventBus = eventBus;
		this.scripService = scripService;
		this.serializationService = serializationService;
		
		setCenter(listView);
		eventBus.register(this);
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
	
	@Override
	public void loadView() {
		final List<Scrip> scrips = scripService.findAll();
		final ExplorerViewState state = serializationService.deserialize(ExplorerViewState.class);
		Platform.runLater(() -> {
			listView.getItems().setAll(scrips);
			Optional.ofNullable(state).ifPresent(value -> {
				Optional.ofNullable(state.getSelectedItem())
					.map(scripService::findByIsin)
					.ifPresent(listView.getSelectionModel()::select);
				Optional.ofNullable(state.getFirstVisibleItem())
					.map(scripService::findByIsin)
					.ifPresent(listView::scrollTo);
			});
		});
	}
	
	@Override
	public void unloadView() {
		final ExplorerViewState state = new ExplorerViewState();
		final ListViewSkin<?> listViewSkin = (ListViewSkin<?>) listView.getSkin();
	    final VirtualFlow<?> virtualFlow = (VirtualFlow<?>) listViewSkin.getChildren().get(0);
	    final Scrip firstVisibleItem = (Scrip) Optional.ofNullable( virtualFlow.getFirstVisibleCell()).map(IndexedCell::getItem).orElse(null);
		state.setFirstVisibleItem(Optional.ofNullable(firstVisibleItem).map(Scrip::getIsin).orElse(null));
	    final String selectedItem = Optional.ofNullable(listView.getSelectionModel().getSelectedItem())
				.map(Scrip::getIsin).orElse(null);
	    state.setSelectedItem(selectedItem);
	    serializationService.serialize(state);
	}
	
}
