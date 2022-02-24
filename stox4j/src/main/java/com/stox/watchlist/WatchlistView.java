package com.stox.watchlist;

import java.util.Optional;

import com.stox.charting.ChartingView;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class WatchlistView extends BorderPane {

	private final ChartingView chartingView;
	private final ScripService scripService;
	private final WatchlistService watchlistService;
	private final ListView<String> listView = new ListView<>();
	private final ComboBox<Watchlist> comboBox = new ComboBox<>();
	
	public WatchlistView(WatchlistService watchlistService, ScripService scripService, ChartingView chartingView) {
		this.chartingView = chartingView;
		this.scripService = scripService;
		this.watchlistService = watchlistService;
		
		listView.setCellFactory(this::createCell);
		final ChangeListener<? super Watchlist> changeListener = this::onWatchlistChanged;
		comboBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
		listView.getSelectionModel().selectedItemProperty().addListener(this::onWatchlistEntrySelected);
		watchlistService.findAll(comboBox.getItems()::add, this::onWatchlistsLoaded);
	}
	
	private void onWatchlistEntrySelected(ObservableValue<? extends String> observable, String old, String isin) {
		Optional.ofNullable(scripService.findByIsin(isin)).ifPresent(chartingView::setScrip);
	}
	
	private void onWatchlistsLoaded() {
		FXCollections.sort(comboBox.getItems());
		if(!comboBox.getItems().isEmpty()) {
			comboBox.getSelectionModel().selectFirst();
		}
	}
	
	private void onWatchlistChanged(ObservableValue<? extends Watchlist> observable, Watchlist old, Watchlist watchlist) {
		if(null != watchlist && null != watchlist.getEntries()) {
			listView.getItems().setAll(watchlist.getEntries());
			if(!watchlist.getEntries().isEmpty()) listView.getSelectionModel().selectFirst();
		} else {
			listView.getItems().clear();
		}
	}
	
	private ListCell<String> createCell(ListView<String> listView){
		return new ListCell<String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(empty || null == item || 0 == item.trim().length()) {
					setText(null);
				} else {
					final Scrip scrip = scripService.findByIsin(item);
					setText(null == scrip ? item : scrip.getName());
				}
			}
		};
	}
	
	
}
