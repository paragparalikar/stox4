package com.stox;

import java.util.concurrent.Executor;

import com.stox.charting.ChartingView;
import com.stox.common.ui.Fx;
import com.stox.common.ui.MessagePanel;
import com.stox.example.AddAsExampleMenu;
import com.stox.example.ExampleTab;
import com.stox.explorer.ExplorerTab;
import com.stox.ranker.RankerTab;
import com.stox.screener.ScreenerTab;
import com.stox.watchlist.WatchlistTab;
import com.stox.watchlist.menu.AddToWatchlistMenu;

import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.StackPane;

public class StoxApplicationRoot extends StackPane {
	
	private final StoxApplicationContext context;
	private final ChartingView chartingView;
	private final ExplorerTab explorerTab;
	private final ExampleTab exampleTab;
	private final RankerTab rankerTab;
	private final ScreenerTab screenerTab;
	private final WatchlistTab watchlistTab;
	private final AddToWatchlistMenu addToWatchlistMenu;
	private final AddAsExampleMenu addAsExampleMenu;
	
	private final TabPane tabPane;
	private final SplitPane splitPane;
	
	public StoxApplicationRoot(StoxApplicationContext context) {
		this.context = context;
		this.chartingView = new ChartingView(context.getHome(), context.getEventBus(), context.getBarService(), 
				context.getScripService(), context.getDrawingService(), context.getSerializationService());
		this.explorerTab = new ExplorerTab(context.getEventBus(), context.getScripService(), context.getSerializationService());
		this.rankerTab = new RankerTab(context.getEventBus(), context.getBarService(), context.getScripService());
		this.screenerTab = new ScreenerTab(context.getEventBus(), context.getBarService(), context.getScripService());
		this.watchlistTab = new WatchlistTab(context.getEventBus(), context.getScripService(), context.getWatchlistService());
		this.addToWatchlistMenu =  new AddToWatchlistMenu(context.getEventBus(), context.getWatchlistService());
		this.exampleTab = new ExampleTab(context.getEventBus(), context.getScripService(), 
				context.getExampleService(), context.getExampleGroupService(), context.getSerializationService());
		this.addAsExampleMenu = new AddAsExampleMenu(context.getEventBus(), context.getExampleService(), context.getExampleGroupService());
		chartingView.getContextMenu().getItems().addAll(addToWatchlistMenu, addAsExampleMenu);
		chartingView.getToolBar().getItems().addAll(Fx.spacer(), new MessagePanel(context.getEventBus()));
		
		this.tabPane = new TabPane(explorerTab, watchlistTab, exampleTab, screenerTab, rankerTab);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		this.splitPane = new SplitPane(tabPane, chartingView);
		getChildren().add(splitPane);
	}
	
	public void load() {
		final Executor executor = context.getExecutor();
		executor.execute(() -> {
			addAsExampleMenu.load();
			Platform.runLater(addAsExampleMenu::show);
		});
		executor.execute(() -> {
			addToWatchlistMenu.load();
			Platform.runLater(addToWatchlistMenu::show);
		});
		executor.execute(() -> {
			explorerTab.load();
			Platform.runLater(explorerTab::show);
		});
		executor.execute(() -> {
			exampleTab.load();
			Platform.runLater(exampleTab::show);
		});
		executor.execute(() -> {
			chartingView.load();
			Platform.runLater(chartingView::show);
		});
	}
	
	public void show() {
		tabPane.setSide(Side.LEFT);
		splitPane.setDividerPositions(0.2);
	}
	
	public void unload() {
		explorerTab.unload();
		exampleTab.unload();
		chartingView.unload();
	}
}
