package com.stox.watchlist;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.SerializationService;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.View;
import com.stox.watchlist.menu.WatchlistContextMenu;
import com.stox.watchlist.menu.WatchlistControlsMenuButton;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class WatchlistView extends BorderPane implements View {

	private final ScripService scripService;
	private final WatchlistService watchlistService;
	private final WatchlistComboBox watchlistComboBox;
	private final WatchlistEntryView watchlistEntryView;
	private final SerializationService serializationService;
	
	public WatchlistView(
			EventBus eventBus, 
			WatchlistService watchlistService, 
			ScripService scripService,
			SerializationService serializationService) {
		this.scripService = scripService;
		this.watchlistService = watchlistService;
		this.serializationService = serializationService;

		this.watchlistComboBox = new WatchlistComboBox(eventBus);
		final WatchlistControlsMenuButton watchlistButtonBar = new WatchlistControlsMenuButton(watchlistComboBox, watchlistService);
		final WatchlistTitleBar watchlistTitleBar = new WatchlistTitleBar(watchlistComboBox, watchlistButtonBar);
		this.watchlistEntryView = new WatchlistEntryView(eventBus, scripService, watchlistComboBox);
		final WatchlistEntryCellFactory watchlistEntryCellFactory = new WatchlistEntryCellFactory(scripService, watchlistService, watchlistComboBox);
		final WatchlistContextMenu watchlistContextMenu = new WatchlistContextMenu(watchlistEntryView, watchlistService, watchlistComboBox);
		
		watchlistEntryView.setContextMenu(watchlistContextMenu);
		watchlistEntryView.setCellFactory(watchlistEntryCellFactory);
		
		setTop(watchlistTitleBar);
		setCenter(watchlistEntryView);
	}
	
	@Override
	public void loadView() {
		final List<Watchlist> watchlists = watchlistService.findAll();
		watchlists.sort(Comparator.comparing(Watchlist::getName));
		final WatchlistViewState state = serializationService.deserialize(WatchlistViewState.class);
		Platform.runLater(() -> {
			watchlistComboBox.getItems().addAll(watchlists);
			if(null != state) {
				watchlists.stream()
					.filter(watchlist -> watchlist.getName().equals(state.getSelectedWatchlistName()))
					.findFirst().ifPresent(watchlistComboBox.getSelectionModel()::select);
				Optional.ofNullable(state.getSelectedWatchlistEntry())
					.ifPresent(watchlistEntryView.getSelectionModel()::select);
			}
		});
	}
	
	@Override
	public void unloadView() {
		final WatchlistViewState state = new WatchlistViewState();
		Optional.ofNullable(watchlistComboBox.getValue())
			.map(Watchlist::getName)
			.ifPresent(state::setSelectedWatchlistName);
		Optional.ofNullable(watchlistEntryView.getSelectionModel().getSelectedItem())
			.ifPresent(state::setSelectedWatchlistEntry);
		serializationService.serialize(state);
	}
}