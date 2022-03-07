package com.stox.watchlist;

import java.util.Iterator;
import java.util.Objects;

import com.stox.charting.ChartingView;
import com.stox.common.scrip.Scrip;
import com.stox.common.ui.Fx;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class AddToWatchlistMenu extends Menu {

	private final ChartingView chartingView;
	private final WatchlistService watchlistService;
	
	public AddToWatchlistMenu(ChartingView chartingView, WatchlistService watchlistService) {
		super("Add to watchlist");
		this.chartingView = chartingView;
		this.watchlistService = watchlistService;
	}
	
	public void init() {
		watchlistService.findAll().forEach(this::createManuItem);
		watchlistService.onWatchlistAdded(this::createManuItem);
		watchlistService.onWatchlistRemoved(this::removeMenuItem);
	}
	
	private void removeMenuItem(Watchlist watchlist) {
		final Iterator<MenuItem> iterator = getItems().iterator();
		while(iterator.hasNext()) {
			final MenuItem item = iterator.next();
			if(Objects.equals(item.getText(), watchlist.getName())) {
				iterator.remove();
				break;
			}
		}
	}
	
	private void createManuItem(Watchlist watchlist) {
		final MenuItem menuItem = new MenuItem();
		getItems().add(menuItem);
		menuItem.textProperty().bind(watchlist.nameProperty());
		menuItem.setOnAction(event -> add(watchlist));
	}

	private void add(Watchlist watchlist) {
		final Scrip scrip = chartingView.getContext().getScripProperty().get();
		if(null != scrip && !watchlist.getEntries().contains(scrip.getIsin())) {
			watchlistService.addEntry(watchlist.getName(), scrip.getIsin());
			Fx.run(() -> watchlist.getEntries().add(scrip.getIsin()));
		}
	}
}
