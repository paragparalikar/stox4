package com.stox;

import com.stox.charting.ChartingContext;
import com.stox.charting.ChartingView;
import com.stox.common.bar.BarRepository;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.ScripRepository;
import com.stox.common.scrip.ScripService;
import com.stox.explorer.ExplorerView;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StoxApplication extends Application {
	
	public static void main(String[] args) {
		LauncherImpl.launchApplication(StoxApplication.class, null, args);
	}
	
	private final BarRepository barRepository = new BarRepository();
	private final BarService barService = new BarService(barRepository);
	private final ScripRepository scripRepository = new ScripRepository();
	private final ScripService scripService = new ScripService(scripRepository);
	private final ChartingContext context = new ChartingContext();
	private final ChartingView chartingView = new ChartingView(context, barService);
	private final ExplorerView explorerView = new ExplorerView(scripService, context);
	private final TabPane tabPane = new TabPane(new Tab("Explorer", explorerView));
	private final SplitPane splitPane = new SplitPane(tabPane, chartingView);
	private final Scene scene = new Scene(splitPane);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		tabPane.setSide(Side.BOTTOM);
		
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setOnShown(this::onShown);
		primaryStage.show();
	}
	
	private void onShown(WindowEvent event) {
		splitPane.setDividerPositions(0.2);
	}
	
}