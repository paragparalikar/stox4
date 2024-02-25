package com.stox;

import com.stox.common.ui.Icon;
import com.stox.data.downloader.DataDownloader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StoxApplication extends Application {
	
	private StoxApplicationRoot root;
	private StoxApplicationContext context;
	
	@Override
	public void init() throws Exception {
		super.init();
		Font.loadFont(Icon.class.getClassLoader().getResource(Icon.PATH).toExternalForm(), 10);
		//Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
		this.context = new StoxApplicationContext();
		this.root = new StoxApplicationRoot(context);
		this.root.loadView();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		final Scene scene = new Scene(root);
		scene.getStylesheets().addAll("style/css/common.css", 
				"style/css/check-box.css", "style/css/choice-box.css",
				"style/css/progress-bar.css", "style/css/progress-indicator.css",
				"style/css/combo-box.css", "style/css/list-view.css",
				"style/css/table-view.css", "style/css/tab-pane.css",
				"style/css/scroll-bar.css", "style/css/menu.css",
				"style/css/charting.css", "style/css/charting-controls.css", 
				"style/css/modal.css", "style/css/form.css"
				);
		primaryStage.setTitle("Stox4j by Parag Paralikar (parag.paralikar@gmail.com)");
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setOnShown(this::onShown);
		primaryStage.show();
	}
	
	private void onShown(WindowEvent event) {
		root.show();
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

	@Override
	public void stop() throws Exception {
		root.unloadView();
	}
	
}