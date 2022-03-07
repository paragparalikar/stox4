package com.stox.watchlist;

import com.stox.charting.ChartingView;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class WatchlistTab extends Tab {

	private final ChartingView chartingView;
	private final ScripService scripService;
	private final WatchlistService watchlistService;
	
	public WatchlistTab(ChartingView chartingView, ScripService scripService, 
			WatchlistService watchlistService) {
		super("Watchlists");
		this.chartingView = chartingView;
		this.scripService = scripService;
		this.watchlistService = watchlistService;
		final Label graphics = new Label(Icon.BOOKMARK);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		setOnSelectionChanged(event -> init());
	}
	
	private void init() {
		if(isSelected() && null == getContent()) {
			final WatchlistView watchlistView = new WatchlistView(watchlistService, scripService, chartingView);
			setContent(watchlistView);
			watchlistView.init();
		}
	}
	
}
