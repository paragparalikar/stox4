package com.stox.watchlist.menu;

import com.stox.common.ui.Fx;
import com.stox.common.ui.Icon;
import com.stox.watchlist.Watchlist;
import com.stox.watchlist.WatchlistComboBox;
import com.stox.watchlist.WatchlistService;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class WatchlistContextMenu extends ContextMenu {

	private final ListView<String> listView;
	private final WatchlistService watchlistService;
	private final WatchlistComboBox watchlistComboBox;
	
	public WatchlistContextMenu(
			ListView<String> listView, 
			WatchlistService watchlistService,
			WatchlistComboBox watchlistComboBox) {
		this.listView = listView;
		this.watchlistService = watchlistService;
		this.watchlistComboBox = watchlistComboBox;
		getItems().add(createSendToTopMenuItem());
		getItems().add(createSendToBottomMenuItem());
	}
	
	private void moveToIndex(Watchlist watchlist, String isin, int index) {
		watchlist.getEntries().remove(isin);
		watchlist.getEntries().add(index, isin);
		System.out.println("Before Update " + watchlist.getEntries().size());
		watchlistService.update(watchlist);
		System.out.println("After Update " + watchlist.getEntries().size());
	}
	
	private MenuItem createSendToTopMenuItem() {
		final MenuItem menuItem = new MenuItem("Send to top", Fx.icon(Icon.CHEVRON_UP));
		menuItem.setOnAction(event -> {
			final Watchlist watchlist = watchlistComboBox.getValue();
			System.out.println("Send to top for " + watchlist.getEntries().size());
			final String isin = listView.getSelectionModel().getSelectedItem();
			System.out.println("Send to top for isin " + isin);
			if(null != watchlist && null != isin) moveToIndex(watchlist, isin, 0);
		});
		return menuItem;
	}
	
	private MenuItem createSendToBottomMenuItem() {
		final MenuItem menuItem = new MenuItem("Send to bottom", Fx.icon(Icon.CHEVRON_DOWN));
		menuItem.setOnAction(event -> {
			final Watchlist watchlist = watchlistComboBox.getValue();
			final String isin = listView.getSelectionModel().getSelectedItem();
			if(null != watchlist && null != isin) moveToIndex(watchlist, isin, watchlist.getEntries().size() - 1);
		});
		return menuItem;
	}
}
