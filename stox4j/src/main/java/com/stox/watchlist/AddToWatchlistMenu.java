package com.stox.watchlist;

import java.util.List;
import java.util.Optional;

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
		watchlistService.findAll().whenComplete(this::onWatchlistsLoaded);
	}
	
	private void onWatchlistsLoaded(List<Watchlist> watchlists, Throwable error) {
		Optional.ofNullable(error).ifPresent(Throwable::printStackTrace);
		Optional.ofNullable(watchlists).ifPresent(this::createMenuItems);
	}
	
	private void createMenuItems(List<Watchlist> watchlists) {
		watchlists.forEach(this::createManuItem);
	}
	
	private void createManuItem(Watchlist watchlist) {
		final MenuItem menuItem = new MenuItem();
		getItems().add(menuItem);
		menuItem.textProperty().bind(watchlist.nameProperty());
		menuItem.setOnAction(event -> {
			final Scrip scrip = chartingView.getContext().getScripProperty().get();
			if(null != scrip && !watchlist.getEntries().contains(scrip.getIsin())) {
				watchlistService.addEntry(watchlist.getName(), scrip.getIsin())
					.whenComplete((nothing, error) -> {
						if(null == error) {
							Fx.run(() -> watchlist.getEntries().add(scrip.getIsin()));
						} else {
							error.printStackTrace();
						}
					});
			}
		});
	}

}
