package com.stox.watchlist;

import com.stox.charting.ChartingView;
import com.stox.common.scrip.Scrip;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class AddToWatchlistMenu extends Menu {

	private final ChartingView chartingView;
	private final WatchlistView watchlistView;
	private final WatchlistService watchlistService;
	
	public AddToWatchlistMenu(ChartingView chartingView, WatchlistView watchlistView, WatchlistService watchlistService) {
		super("Add to watchlist");
		this.chartingView = chartingView;
		this.watchlistView = watchlistView;
		this.watchlistService = watchlistService;
		
	}
	
	private void createManuItem(Watchlist watchlist) {
		final MenuItem menuItem = new MenuItem();
		menuItem.textProperty().bind(watchlist.nameProperty());
		menuItem.setOnAction(event -> {
			final Scrip scrip = chartingView.getContext().getScripProperty().get();
			if(null != scrip && !watchlist.getEntries().contains(scrip.getIsin())) {
				
			}
		});
	}

}
