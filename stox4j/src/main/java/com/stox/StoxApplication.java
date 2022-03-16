package com.stox;

import com.stox.charting.ChartingView;
import com.stox.common.ui.Fx;
import com.stox.common.ui.Icon;
import com.stox.common.ui.MessagePanel;
import com.stox.data.downloader.DataDownloader;
import com.stox.example.AddAsExampleMenu;
import com.stox.example.ExampleTab;
import com.stox.explorer.ExplorerTab;
import com.stox.ranker.RankerTab;
import com.stox.screener.ScreenerTab;
import com.stox.watchlist.AddToWatchlistMenu;
import com.stox.watchlist.WatchlistTab;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StoxApplication extends Application {
	
	public static void main(String[] args) {
		LauncherImpl.launchApplication(StoxApplication.class, null, args);
	}
	
	private Scene scene;
	private StackPane root;
	private TabPane tabPane;
	private SplitPane splitPane;
	private StoxApplicationContext context;
	
	@Override
	public void init() throws Exception {
		super.init();
		Font.loadFont(Icon.class.getClassLoader().getResource(Icon.PATH).toExternalForm(), 10);
		this.context = new StoxApplicationContext();
		final ChartingView chartingView = new ChartingView(context.getEventBus(), context.getBarService(), context.getScripService());
		final ExplorerTab explorerTab = new ExplorerTab(context.getEventBus(), context.getScripService());
		final RankerTab rankerTab = new RankerTab(context.getEventBus(), context.getBarService(), context.getScripService());
		final ScreenerTab screenerTab = new ScreenerTab(context.getEventBus(), context.getBarService(), context.getScripService());
		final WatchlistTab watchlistTab = new WatchlistTab(context.getEventBus(), context.getScripService(), context.getWatchlistService());
		final AddToWatchlistMenu addToWatchlistMenu =  new AddToWatchlistMenu(context.getEventBus(), context.getWatchlistService());
		addToWatchlistMenu.init();
		final ExampleTab exampleTab = new ExampleTab(context.getEventBus(), context.getScripService(), context.getExampleService(), context.getExampleGroupService());
		final AddAsExampleMenu addAsExampleMenu = new AddAsExampleMenu(context.getEventBus(), context.getExampleService(), context.getExampleGroupService());
		addAsExampleMenu.init();
		chartingView.getContextMenu().getItems().addAll(addToWatchlistMenu, addAsExampleMenu);
		chartingView.getToolBar().getItems().addAll(Fx.spacer(), new MessagePanel(context.getEventBus()));
		tabPane = new TabPane(explorerTab, watchlistTab, exampleTab, screenerTab, rankerTab);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		splitPane = new SplitPane(tabPane, chartingView);
		root = new StackPane(splitPane);
		scene = new Scene(root);
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().addAll("style/css/common.css", 
				"style/css/check-box.css", "style/css/choice-box.css",
				"style/css/progress-bar.css", "style/css/progress-indicator.css",
				"style/css/combo-box.css", "style/css/list-view.css",
				"style/css/table-view.css", "style/css/tab-pane.css",
				"style/css/scroll-bar.css", "style/css/menu.css",
				"style/css/charting.css", "style/css/charting-controls.css", 
				"style/css/modal.css", "style/css/form.css");
		primaryStage.setTitle("Stox4j by Parag Paralikar (parag.paralikar@gmail.com)");
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setOnShown(this::onShown);
		primaryStage.show();
	}
	
	private void onShown(WindowEvent event) {
		tabPane.setSide(Side.LEFT);
		splitPane.setDividerPositions(0.2);
		
		DataDownloader.builder()
			.executor(context.getExecutor())
			.eventBus(context.getEventBus())
			.barService(context.getBarService())
			.scripService(context.getScripService())
			.eodBarDownloader(context.getEodBarDownloader())
			.scripMasterDownloader(context.getScripMasterDownloader())
			.build()
			.download();
	}
	
}