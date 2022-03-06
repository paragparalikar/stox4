package com.stox;

import com.stox.charting.ChartingView;
import com.stox.common.bar.BarRepository;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.ScripRepository;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;
import com.stox.explorer.ExplorerView;
import com.stox.watchlist.WatchlistRepository;
import com.stox.watchlist.WatchlistService;
import com.stox.watchlist.WatchlistView;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

public class StoxApplication extends Application {
	
	public static void main(String[] args) {
		LauncherImpl.launchApplication(StoxApplication.class, null, args);
	}
	
	private Scene scene;
	private StackPane root;
	private TabPane tabPane;
	private SplitPane splitPane;
	
	@Override
	public void init() throws Exception {
		super.init();
		Font.loadFont(Icon.class.getClassLoader().getResource(Icon.PATH).toExternalForm(), 10);
		final BarRepository barRepository = new BarRepository();
		final BarService barService = new BarService(barRepository);
		final ScripRepository scripRepository = new ScripRepository();
		final ScripService scripService = new ScripService(scripRepository);
		final ChartingView chartingView = new ChartingView(barService);
		final ExplorerView explorerView = new ExplorerView(scripService, chartingView);
		final DynamoDbAsyncClient dynamoDbAsyncClient = DynamoDbAsyncClient.builder().build();
		final WatchlistRepository watchlistRepository = new WatchlistRepository(dynamoDbAsyncClient);
		final WatchlistService watchlistService = new WatchlistService(watchlistRepository);
		final WatchlistView watchlistView = new WatchlistView(watchlistService, scripService, chartingView);
		tabPane = new TabPane(new Tab("Explorer", explorerView), new Tab("Watchlist", watchlistView));
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
		
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setOnShown(this::onShown);
		primaryStage.show();
	}
	
	private void onShown(WindowEvent event) {
		tabPane.setSide(Side.BOTTOM);
		splitPane.setDividerPositions(0.2);
	}
	
}